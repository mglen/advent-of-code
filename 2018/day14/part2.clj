
(defn recipes-until [start run-till]
  (let [till-length (count run-till)]
    (loop [recipes start
           elf-indexes [0 1]
           recipes-count (count start)
           double-count false]
      (condp = [double-count run-till]
        [false (take-last till-length recipes)] (- recipes-count till-length)
        [true (take-last till-length recipes)] (- recipes-count till-length)
        [true (take till-length (take-last (+ 1 till-length) recipes))] (- recipes-count 1 till-length)
        (let [last-recipes (map (partial nth recipes) elf-indexes)
              tmp-score (apply + last-recipes)
              recipes-to-add (if (> tmp-score 9) [1 (mod tmp-score 10)] [tmp-score])
              new-recipes (apply conj recipes recipes-to-add)
              new-recipes-count (+ recipes-count (count recipes-to-add))
              new-indexes (map #(mod % new-recipes-count) (map (partial + 1) elf-indexes last-recipes))]
  ;        (println "last-recipes" last-recipes "tmp-score" tmp-score "to-add" recipes-to-add "new-indexes" new-indexes "new-count" new-recipes-count)
          (recur
                 new-recipes
                 new-indexes
                 new-recipes-count
                 (= (count recipes-to-add) 2)))))))
      
(let [start [3 7]
      run-till [7 6 0 2 2 1]]
  (println (recipes-until start run-till)))

; 4113836210 too high
