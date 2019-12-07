(ns aoc2019.day3-test
  (:require [clojure.test :refer :all]
            [aoc2019.day3 :refer :all]))

(deftest examples
  (testing "First example"
    (is (= [3 3] (get-closest-intersection
                   "R8,U5,L5,D3"
                   "U7,R6,D4,L4"))))
  (testing "Second example"
    (is (= 159 (reduce + (get-closest-intersection
                          "R75,D30,R83,U83,L12,D49,R71,U7,L72"
                          "U62,R66,U55,R34,D71,R55,D58,R83")))))
  (testing "Third example"
    (is (= 159 (reduce + (get-closest-intersection
                          "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
                          "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"))))))
