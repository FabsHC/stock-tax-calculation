run-file:
	cat $(FILE) | clj -M -m stocks.core

run:
	echo '$(INPUT)' | clj -M -m stocks.core

# Clojure CI commands

ci-install-dependencies:
	lein deps

ci-debug-classpath:
	lein classpath

ci-tests:
	lein test

ci-code-formatting:
	lein cljfmt check