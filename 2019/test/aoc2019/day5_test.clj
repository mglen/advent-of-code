(ns aoc2019.day5-test
  (:require [clojure.test :refer :all]
            [aoc2019.day5 :refer :all]))

(deftest examples
  (testing "First example"
    (is (= [3 3] (run {:memory [3 0 4 0 99] :cursor 0})))))
