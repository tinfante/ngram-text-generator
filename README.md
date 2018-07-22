# ngram-text-generator

When I read the [NLTK Book](https://www.nltk.org/book/) years ago, there was a part about generating text with [n-gram models](https://en.wikipedia.org/wiki/N-gram#n-gram_models) which really fascinated me. As more n-grams were used, the text became less random and more human-like, but still different from the input text. This really made me want to understand how such an effect could be achieved and learn more about NLP. It's something silly and fun that I felt like recreating.

When creating the (n-1)-order Markov model actual probabilities are not stored. Instead, for the (n-1) length sequence that predicts the following tokens, each individual occurrence of following tokens is stored. So we have the following map structure.

```
$ lein repl
```
For a bigram model:
```clojure
ngram-text-generator.core=> (def genesis-bigram-model (make-ngram-model (make-ngrams (tokenize (read-file "resources/texts/genesis.txt")) 2)))
#'ngram-text-generator.core/genesis-bigram-model

ngram-text-generator.core=> (take 5 genesis-bigram-model)
([("away") ["from" "from" "," "." "from" "from" "the" "the" "." "." "from" "from" "the" "the" "." "." "their" "from" "from" "from" "in" "your" "my" "my" "from" "from" "-" "," ";" "to" "my" "my" "my" "," "the" "from" "all" "my" "with" "empty-handed" "." "the" "," "," "from" "with" "from"]]
 [("sat") ["at" "down" "opposite" "on" "down" "down" "up"]]
 [("return") ["to" "." "to" "from" "to" "to" "the" "for" "to" "every" "."]]
 [("slay") ["the"]]
 [("stars") ["." "," "of" "of" "were"]])
```
For a trigram model:
```clojure
ngram-text-generator.core=> (def genesis-trigram-model (make-ngram-model (make-ngrams (tokenize (read-file "resources/texts/genesis.txt")) 3)))
#'ngram-text-generator.core/genesis-trigram-model

ngram-text-generator.core=> (take 5 genesis-trigram)
([("Then" "I") ["asked" "bowed" "awoke" "will"]]
 [("When" "Abraham") ["complained" "'s"]]
 [("and" "saw") ["that" "that" "three" "the" "the" "a" "him" "that" "Esau" "the" "that" "his"]
 [("Jacob" "and") ["said" "blessed" "sent" "all" "his"]]
 [("like" "a") ["hairy" "whore" "lion" "lioness"]])
```

An example below, generating text with a bigram, trigram and four-gram model of the Book of Genesis.

```
$ lein run genesis.txt 2 100
In the clans Teman, "Tell me also brought out to meet us make a very much
as food. Early in the head of Enoch eight days of Eden to Joseph and sin,
and kept you are you, and knew that Laban, please," Then Judah saw Ephraim,
a company that he died. So the captain of On the great lights - that you in
the firstborn bore Tubal-cain, I buried, and gave three years; and she
conceived and I have conceived and the girl was the field, to lead

$ lein run genesis.txt 3 100
In the beginning when God caused me to say, 'Please offer your jar. "The
man solemnly warned us, came up after the death of Abraham his master and
swore to him as he sat at the place that God has heard the words of his son
Esau were told to Rebekah; so Noah knew that they had acquired in the land,
Joseph had interpreted to us and be gone." So Esau returned that day the
Israelites. Bela son of Zerah of Bozrah succeeded him as favorably as he
pastured the donkeys. When people

$ lein run genesis.txt 4 100
In the beginning when God created the heavens and the earth, birds,
domestic animals, and every animal of the earth shall be blessed in you and
in your offspring. Know that I am old; I do not know; am I my brother's
keeper? "And he believed the LORD; we cannot speak to you anything bad or
good. Look, that city is near enough to flee to, and it shall be a wild ass
of a man, with his hand against everyone, and everyone's hand against him;
and
```

It would be interesting to see what happens when using a lot more data and longer n-gram sequences (e.g. Google Books Ngrams, but only has up to 5-grams).
