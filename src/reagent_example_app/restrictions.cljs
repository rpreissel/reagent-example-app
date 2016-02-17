(ns reagent-example-app.restrictions
  (:require-macros [reagent-example-app.restrictions :refer [defrestriction]]))


(defrestriction not-empty-string [value]
  "Enter at least one character."
  (pos? (count value)))

(defrestriction at-least-eight-characters [value]
  "At least 8 characters long."
  (>= (count value) 8))

(defrestriction uppercase-letters [value]
  "Contains uppercase letters."
  (re-find #"[A-Z]" value))

(defrestriction lowercase-letters [value]
  "Contains lowercase letters."
  (re-find #"[a-z]" value))

(defrestriction numbers [value]
  "Contains numbers."
  (re-find #"\d" value))

(defrestriction punctuation [value]
  "Contains punctuation."
  (re-find (re-pattern "[!\"#$%&\\'()*+,-./:;<=>?@_`|~]") value))


(def default-restrictions [
                            ;;not-empty-string
                            at-least-eight-characters
                            uppercase-letters
                            lowercase-letters
                            numbers
                            punctuation])

