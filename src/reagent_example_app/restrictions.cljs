(ns reagent-example-app.restrictions)



(def not-empty-string
  { :label "Enter at least one character."
    :validate (fn [value]
                (pos? (count value)))})

(def at-least-eight-characters
  { :label "At least 8 characters long."
    :validate (fn [value]
                (>= (count value) 8))})

(def uppercase-letters
  { :label "Contains uppercase letters."
    :validate (fn [value]
                (and value (re-find #"[A-Z]" value)))})

(def lowercase-letters
  { :label "Contains lowercase letters."
    :validate (fn [value]
                (and value (re-find #"[a-z]" value)))})

(def numbers
  { :label "Contains numbers."
    :validate (fn [value]
                (and value (re-find #"\d" value)))})

(def punctuation
  { :label "Contains punctuation."
    :validate (fn [value]
                (and value (re-find (re-pattern "[!\"#$%&\\'()*+,-./:;<=>?@_`|~]") value)))})


(def default-restrictions [
                           ;;not-empty-string
                           at-least-eight-characters
                           uppercase-letters
                           lowercase-letters
                           numbers
                           punctuation])
