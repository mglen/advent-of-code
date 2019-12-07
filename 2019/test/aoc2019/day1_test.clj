(ns aoc2019.day1-test
  (:require [clojure.test :refer :all]
            [aoc2019.day1 :refer :all]))

(deftest examples
  (testing "First example"
    (is (= 2 (get-fuel 12))))
  (testing "Second example"
    (is (= 2 (get-fuel 14))))
  (testing "Third example"
    (is (= 654 (get-fuel 1969))))
  (testing "Fourth example"
    (is (= 33583 (get-fuel 100756)))))
