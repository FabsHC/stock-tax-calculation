# stock-tax-calculation
![Clojure](https://img.shields.io/badge/Clojure-%23Clojure.svg?style=for-the-badge&logo=Clojure&logoColor=Clojure)

FYI: The Golang version of this project: [capital-gain](https://github.com/FabsHC/capital-gain)

This project simulates the purchase and sale of shares. It does not use any database, all data is stored in memory during the execution of a list of operations.

The application will receive a list of operations that it can execute purchase and sale, returning for each operation how much tax was paid.
The operations will be in the order in which they occurred, that is, the second operation in the list happened after the first and so on.
Each line is an independent simulation, the program will not maintain the state obtained in one line for the others.

Below we have the application input and output data.<br>

Input:

| Name              | Detail                                                           |
|:------------------|:-----------------------------------------------------------------| 
| operation         | Whether the operation is a purchase(buy) or sale(sell) operation |
| unit-cost         | Share unit price in a currency with two decimal places           |
| quantity          | Number of shares traded                                          |

Output:

| Name | Detail                                         |
|:-----|:-----------------------------------------------| 
| tax  | Amount of tax paid for the operation performed |

In the [resources](doc/resources) folder we have several examples to run the application.
In the [CASES.md](doc/CASES.md) we are detailing these examples.

## Project Organization
```
├── /src
    └── /stocks
        ├── /models................: Model map functions
        ├── /services..............: Business logic functions
        ├── /util..................: Util functions 
        └── core.clj...............: Main application file
└── /test..........................: Unit tests file
```

## How to run
The application expects data in STDIN format and will return json in STDOUT format. So you can provide a JSON or a file containing several lines with each line having a JSON.
Examples below:
- Direct JSON input
    ```bash
    make run INPUT='[{"operation":"buy", "unit-cost":10.00, "quantity": 100}, {"operation":"sell", "unit-cost":15.00, "quantity": 50}, {"operation":"sell", "unit-cost":15.00, "quantity": 50}]'
    ```
- Reading file
    ```bash
    make run-file FILE=doc/resources/case_1
    ```
- Docker direct input
    ```bash
    WIP 
    ```
- Docker reading file for input
    ```bash
    WIP 
    ```
## How to run the tests
WIP

## Lint and Coverage
WIP
