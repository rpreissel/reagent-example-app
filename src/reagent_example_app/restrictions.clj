(ns reagent-example-app.restrictions)

(defmacro defrestriction [name paras description & body]
  `(defn ~name ~paras
       (let [checked# (and ~@paras (do ~@body))]
         {:label ~description
          :checked (boolean checked#)})))
