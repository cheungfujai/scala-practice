package democracy

enum Grade:
  case Bad, Mediocre, Inadequate, Passable, Good, VeryGood, Excellent

object Grade:
  def median(grades: Seq[Grade]): Grade = grades.sortBy(_.ordinal).apply(grades.size / 2)

end Grade

case class Candidate(name: String)

case class Ballot(grades: Map[Candidate, Grade])

case class Election(description: String, candidates: Set[Candidate]):
  def elect(ballots: Seq[Ballot]): Candidate =
    assert(ballots.nonEmpty)
    assert(ballots.forall(_.grades.keySet == candidates))
    val allGrades: Seq[(Candidate, Grade)] = ballots.flatMap(_.grades.map((candidate, grade) => (candidate, grade)))
    val gradesPerCandidate: Map[Candidate, Seq[Grade]] = allGrades.groupMap(_._1)(_._2)
    findWinner(gradesPerCandidate)
  end elect

  def findWinner(gradesPerCandidate: Map[Candidate, Seq[Grade]]): Candidate =
    if gradesPerCandidate.forall((candidate, grades) => grades.isEmpty) then
      val candidatesSeq = gradesPerCandidate.keys.toSeq
      val randomIndex   = util.Random.between(0, candidatesSeq.size)
      candidatesSeq(randomIndex)
    else
      val bestMedianGrade: Grade = gradesPerCandidate.values.filter(_.nonEmpty).map(grades => Grade.median(grades)).maxBy(_.ordinal)
      val bestCandidates: Map[Candidate, Seq[Grade]] = gradesPerCandidate.filter((_, listGrade) => Grade.median(listGrade) == bestMedianGrade)
      if bestCandidates.size == 1 then bestCandidates.head._1
      else
        val bestCandidatesMinusOneMedianGrade: Map[Candidate, Seq[Grade]] = bestCandidates.map((candidate, grades) => candidate -> grades.diff(List(bestMedianGrade)))
        findWinner(bestCandidatesMinusOneMedianGrade)
  end findWinner

end Election
