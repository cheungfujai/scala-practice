# Overview
Unlike common voting systems, in Majority Judgement voters don’t vote for a single candidate, but they give a grade to each candidate. The candidate with the highest median grade wins the election.

In our implementation, each voter can give a grade among bad, mediocre, inadequate, passable, good, very good, and excellent (from worst to best).

A ballot contains the grades that a voter assigns to each alternative of the election.

We find the winner of the election by computing the median grade received by each alternative. The alternative with the highest median grade is the winner.

In case more than one alternative have the same median grade, there is a tie-break. The tie-breaking procedure consists of removing from each alternative the grades equal to the median grade until only one of the previously tied candidates has the highest median grade.
