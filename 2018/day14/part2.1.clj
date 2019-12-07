(:use [clojure.data.finger-tree :only [double-list]])

(defn recipes-until [start run-till]
  (let [till-length (count run-till)]
    (loop [recipes (apply double-list start)
           elf-indexes [0 1]
           recipes-count (count start)]
      (if (or (= run-till (take-last till-length recipes))
              (= run-till (take till-length (take-last (+ 1 till-length) recipes))))
        recipes-count
        (let [last-recipes (map (partial nth recipes) elf-indexes)
              tmp-score (apply + last-recipes)
              recipes-to-add (if (> tmp-score 9) [1 (mod tmp-score 10)] [tmp-score])
              new-recipes (apply conj recipes recipes-to-add)
              new-recipes-count (+ recipes-count (count recipes-to-add))
              new-indexes (map #(mod % new-recipes-count) (map (partial + 1) elf-indexes last-recipes))]
          (if (= (mod recipes-count 100000) 0) (println "passed" recipes-count) "")
  ;        (println "last-recipes" last-recipes "tmp-score" tmp-score "to-add" recipes-to-add "new-indexes" new-indexes "new-count" new-recipes-count)
          (recur
                 new-recipes
                 new-indexes
                 new-recipes-count))))))
      
(let [start [3 7]
      run-till [7 6 0 2 2 1]]
  (println (recipes-until start run-till)))

; 4113836210 too high
