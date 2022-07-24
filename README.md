# Cat
pet #1

# Description

This project aims to provides core abstraction for articles and products.

# How to build it

```
./gradlew clean check assemble
```

# Further development

- Add simple console app which can read articles and products from a json file.
- Add simple SQL implementation for `ProductRepository` and `Warehouse`:
  - `ProductRepository` implementation is trivial. 
  - The `Warehouse::reserve` method can be implemented with a series of
    ```
    UPDATE articles SET quantityt = quantity - 1 WHERE id = :id RETURNING quantity
    ```
    running in a transaction. The transaction is rolled back whenever returned value is negative.
  - `Reservation.OK::rollback` is perhaps the hardest part of it, may be a reason to rethink the contract. 
- Create a RESTful web app that can also load articles and products from a json file. 
