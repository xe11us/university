(defn constant [value]
    (constantly value))

(def variables (set [
    'x
    'y
    'z
]))

(def variable? variables)

(defn variable [name]
  (fn [eval-map]
    (get eval-map name)))

(defn operation [f]
  (fn [& args]
    (fn [vars]
      (apply f (mapv (fn [x] (x vars)) args)))))

(def add (operation +))
(def subtract (operation -))
(def negate subtract)
(def multiply (operation *))
(defn arity-divide
        ([arg] (/ (double arg)))
        ([arg & args] (reduce (fn [a b] (/ (double a) (double b))) arg args)))
(def divide (operation arity-divide))
(def med (operation (fn [& args] (nth (sort args) (quot (count args) 2)))))
(def avg (operation (fn [& args] (/ (apply + args) (count args)))))

(def operations {
   '+ add
   '- subtract
   '* multiply
   '/ divide
   'negate negate
   'med med
   'avg avg
})

(defn parser [constant variable operations]
    (fn [expression]
        (letfn [(parse [expression]
            (cond
                (number? expression) (constant expression)
                (variable? expression) (variable (str expression))
                (list? expression) (apply (get operations (first expression))
                                                (mapv parse (rest expression)))))]
    (parse (read-string expression)))))

(def parseFunction (parser constant variable operations))

;================================================HW 11==================================================================

(defn proto-get [obj key]
  (cond
    (contains? obj key) (obj key)
    (contains? obj :prototype) (proto-get (obj :prototype) key)
    :else nil))

(defn proto-call [this key & args]
  (apply (proto-get this key) this args))

(defn field [key]
  (fn [this] (proto-get this key)))

(defn method [key]
  (fn [this & args] (apply proto-call this key args)))

(def toString (method :toString))
(def evaluate (method :evaluate))
(def diff (method :diff-rule))
(def args (field :args))

(defn constructor [cons proto]
 (fn [& args] (apply cons {:prototype proto} args)))

(def Constant)
(def ConstantPrototype
  {:toString (fn [this] (str (format "%.1f" ((field :value) this))))
   :evaluate (fn [this & args] ((field :value) this))
   :diff-rule (fn [_ _] (Constant 0))})

(defn ConstantConstructor [this value]
    (assoc this
    :value value))

(def Constant (constructor ConstantConstructor ConstantPrototype))

(def ZERO (Constant 0))
(def ONE (Constant 1))

(def VariablePrototype
 {:toString (fn [this] ((field :name) this))
  :evaluate (fn [this eval-set]
                   (eval-set ((field :name) this)))
  :diff-rule (fn [this var]
  (if (= ((field :name) this) var)
        ONE
        ZERO))})

(defn VariableConstructor [this name]
  (assoc this
   :name name))

(def Variable (constructor VariableConstructor VariablePrototype))

(def OperationPrototype
    (let [function (field :function) symbol (field :symbol) diff-rule (field :diff-rule)]
        {:toString (fn [this] (str "(",  (symbol this), " ", (clojure.string/join " " (mapv toString (args this))), ")"))
         :evaluate (fn [this eval-set]
                    (apply (function this) (mapv (fn [arg] (evaluate arg eval-set)) (args this))))
         :diff-rule (fn [this variable] (diff-rule this variable))}))


(defn OperationConstructor [this & args]
    (assoc this :args args))

(defn make-operation
  [symbol function diff-rule]
  (constructor OperationConstructor {:prototype OperationPrototype
                                     :symbol symbol
                                     :function function
                                     :diff-rule diff-rule
                                    }))

(def diff-args (fn [args variable] (mapv  (fn [x] (diff x variable)) args)))

(def Add)
(defn diff-sum [variable & args]
        (apply Add (diff-args args variable)))

(defn diff-sum-call [this var] (apply diff-sum var (args this)))

(def Add (make-operation '+ + diff-sum-call))
(def Subtract (make-operation '- - (fn [this variable] (apply Subtract (diff-args (args this) variable)))))


(def Multiply)
(defn multiply-rest-diff [args]
    (reduce (fn [x y] (Multiply x y)) (rest args)))

(def Multiply (make-operation '* * (fn [this variable]
    (let [arguments (args this)
          frst (first arguments)
          other (multiply-rest-diff arguments)]
            (Add (Multiply frst (diff other variable))
                 (Multiply other (diff frst variable)))))))

(def Divide (make-operation '/ (fn [x y] (/ (double x) y)) (fn [this variable]
    (let [arguments (args this)
          frst (first arguments)
          other (multiply-rest-diff arguments)]
          (Divide (Subtract (Multiply other (diff frst variable))
                            (Multiply frst (diff other variable)))
                  (Multiply other other))))))

(def Negate (make-operation 'negate - (fn [this variable] (apply Negate (diff-args (args this) variable)))))
(def Sum (make-operation 'sum + diff-sum-call))
(def Avg (make-operation 'avg (fn [& args] (/ (apply + args) (count args))) (fn [this variable]
    (Multiply (Constant (/ (count (args this)))) (diff-sum-call this variable)))))

(def objectOperations {
   '+ Add
   '- Subtract
   '* Multiply
   '/ Divide
   'negate Negate
   'sum Sum
   'avg Avg
})

(def parseObject (parser Constant Variable objectOperations))