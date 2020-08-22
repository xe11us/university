(defn are-equal-size? [objects]
  {:pre  [(some? objects)]
   :post [(boolean? %)]}
  (every? (partial == (count (first objects))) (mapv count objects)))

;=========================================Vector============================================
(defn correct-vector? [object]
  {:pre  [(some? object)]
   :post [(boolean? %)]}
  (and (vector? object) (every? number? object)))

(defn vector-operation [func]
  (fn [& vectors]
    {:pre  [(and (some? vectors) (are-equal-size? vectors) (every? correct-vector? vectors))]
     :post [(some? %)]}
    (apply mapv func vectors)))

(def v+ (vector-operation +))
(def v- (vector-operation -))
(def v* (vector-operation *))


(defn v*s [vector & scalars]
  {:pre  [(and (some? vector) (every? number? scalars) (correct-vector? vector))]
   :post [(some? %)]}
  (mapv (partial * (apply * scalars)) vector))


(defn scalar [& vectors]
  {:pre  [(and (some? vectors) (every? correct-vector? vectors) (are-equal-size? vectors))]
   :post [(some? %)]}
  (apply + (apply v* vectors)))


(defn vect [& vectors]
  {:pre  [(and (some? vectors) (every? correct-vector? vectors) (are-equal-size? vectors) (== (count (first vectors)) 3))]
   :post [(some? %)]}
  (reduce (fn [a b]
            (vector (- (* (nth a 1) (nth b 2)) (* (nth a 2) (nth b 1)))
                    (- (* (nth a 2) (nth b 0)) (* (nth a 0) (nth b 2)))
                    (- (* (nth a 0) (nth b 1)) (* (nth a 1) (nth b 0)))))
  vectors))

;=========================================Matrix============================================

(defn correct-matrix? [object]
  {:pre  [(some? object)]
   :post [(boolean? %)]}
  (and (every? correct-vector? object) (are-equal-size? object)))

; :NOTE: Число столбцов?
(defn matrix-operation [func]
(fn [& matrixes]
  {:pre  [(and (some? matrixes) (every? correct-matrix? matrixes) (are-equal-size? matrixes))]
   :post [(some? %)]}
  (apply mapv func matrixes)))

(def m+ (matrix-operation v+))
(def m- (matrix-operation v-))
(def m* (matrix-operation v*))

(defn m*s [matrix & scalars]
  {:pre  [(and (some? matrix) (correct-matrix? matrix) (every? number? scalars))]
   :post [(and (some? %) (correct-matrix? %))]}
   (let [mult (apply * scalars)]
  (mapv (fn [a] (v*s a mult)) matrix)))

(defn m*v [matrix & vectors]
  {:pre  [(and (some? matrix) (some? vectors) (correct-matrix? matrix) (every? correct-vector? vectors)
               (are-equal-size? vectors) (== (count (first matrix)) (count (first vectors))))]
   :post [(and (some? %) (correct-vector? %))]}
  (mapv (fn [a] (apply scalar a vectors)) matrix))


(defn transpose [matrix]
  {:pre  [(and (some? matrix) (correct-matrix? matrix))]
   :post [(and (some? %) (correct-matrix? %))]}
  (apply mapv vector matrix))


(defn m*m [& matrixes]
  {:pre  [(and (some? matrixes) (every? correct-matrix? matrixes))]
   :post [(and (some? %) (correct-matrix? %))]}
  (reduce
    (fn [a b] (mapv (fn [vector] (m*v (transpose b) vector)) a))
    matrixes))


;=========================================Tensor============================================

(defn correct-tensor? [object]
  {:pre  [(some? object)]
   :post [(boolean? %)]}
  (if (every? vector? object)
    (and (are-equal-size? object) (correct-tensor? (apply vector (apply concat object))))
           (correct-vector? object)))

(defn recurrent-tensor-apply [func & tensors]
  {:pre  [(and (some? tensors)
               (or (every? number? tensors) (and (every? correct-tensor? tensors) (are-equal-size? tensors))))]
   :post [(some? %)]}
  (if (vector? (first tensors))
    (apply mapv (partial recurrent-tensor-apply func) tensors)
    (apply func tensors)))

(defn tensor-operation [func]
  (fn [& tensors]
    {:pre  [(and (some? tensors) (every? correct-tensor? tensors))]
     :post [(some? %)]}
    (apply recurrent-tensor-apply func tensors)))

(def t+ (tensor-operation +))
(def t- (tensor-operation -))
(def t* (tensor-operation *))