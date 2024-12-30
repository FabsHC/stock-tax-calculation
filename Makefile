run-file:
	cat $(FILE) | clj -M -m stocks.core

run:
	echo '$(INPUT)' | clj -M -m stocks.core