# Vulnerability Script Execution

## Project Overview

This project provides a solution to determine a sane execution order for a list of scripts that have dependencies. Each script has a unique `scriptId` and a list of `scriptIds`. The goal is to find an execution order such that all dependencies of a script are executed before the script itself.

## Technologies Used
- **Java 17**: This project is built using Java 17.
- **Maven**: For dependency management and build automation.
- **JUnit 5**: For writing and executing test cases.

