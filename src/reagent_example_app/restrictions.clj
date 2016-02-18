(ns reagent-example-app.restrictions)

(defmacro defrestriction [name paras description & body]
  {:pre [(vector? paras) (= (count paras) 1)]}
  `(defn ~name ~paras
       (let [checked# (and ~@paras (do ~@body))]
         {:label ~description
          :checked (boolean checked#)})))
