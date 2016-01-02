# Constraint Satisfaction Problem Solvers

This program solves CSP's. The first program
is a crossword solver which contains categories
and a list of words in every category. Given a
sequence of numbers x1...xN (which specify the category)
the word at the indices of a category must be filled
with a word from that category. Words may overlap.
A recursive backtracking search algorithm is used
along with arc consistency to optimize run times.

The second progam is a wargame where a user plays
against an AI. Both players take turns picking a square
(where each square has a numeric reward). The person
with the highest reward at the end wins the game. However
the game has constraints such as if you pick a square neighboring
an opponent square, you steal that square from your 
opponent. This program used minimax search along with
alpha beta pruning for optimazition.


