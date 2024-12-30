run-file:
	cat $(FILE) | clj -M -m stocks.core

run:
	echo '$(INPUT)' | clj -M -m stocks.core

# Docker run  WIP
docker-run-file:
	DIR=$$(dirname $(FILE)); \
	FILE_NAME=$$(basename $(FILE)); \
  	echo $$DIR && \
  	echo $$FILE_NAME && \
    docker run --rm -v $(pwd)/$$DIR:/app/resources -w /app clojure:tools-deps cat /app/resources/$$FILE_NAME | clojure -M -m stocks.core

docker-run:
	docker run --rm -v $(pwd):/app -w /app clojure:tools-deps bash -c "echo '$(INPUT)' | clojure -M -m stocks.core"

# Clojure CI commands

ci-install-dependencies:
	lein deps

ci-debug-classpath:
	lein classpath

ci-tests:
	lein test
