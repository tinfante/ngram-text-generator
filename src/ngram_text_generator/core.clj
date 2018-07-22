(ns ngram-text-generator.core
  (:require [opennlp.nlp :as nlp])
  (:gen-class))


(def tokenize
  (nlp/make-tokenizer "resources/models/en-token.bin"))

(def detokenize
  (nlp/make-detokenizer "resources/models/english-detokenizer.xml"))


(defn read-file
  [file-name]
  (slurp file-name))


(defn make-ngrams
  [tokenized-text ngram-order]
  (partition ngram-order 1 tokenized-text))


(defn make-ngram-model
  [ngrams]
  (loop [n ngrams
         m {}]
    (if (empty? n)
      m
      (recur (rest n)
             (let [ftks (butlast (first n))
                   ltk (last (first n))]
               (assoc m ftks (conj (get m ftks []) ltk)))
             ))))


(defn generate-text
  [ngram-model ngram-order initial-tokens num-words]
  (loop [n num-words
         tks (vec initial-tokens)]
    (if (zero? n)
      tks
      (recur
        (dec n)
        (conj tks (rand-nth (ngram-model (take-last (dec ngram-order) tks)))))
      )))


(defn -main
  [file-name ngram-order num-words]
  (let [ngo (Integer. ngram-order)
        nws (Integer. num-words)
        tokens (tokenize (read-file (str "resources/texts/" file-name)))
        model (make-ngram-model (make-ngrams tokens ngo))]
    (println (detokenize (generate-text model ngo (take ngo tokens) nws)))
    ))
