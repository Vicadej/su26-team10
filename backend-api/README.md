# MealPrep Match Backend API


## Customer


| GET | `/api/customers` | List all customers |
| GET | `/api/customers/{id}` | Get one customer |
| POST | `/api/customers` | Create a customer |
| PUT | `/api/customers/{id}` | Update a customer (partial — only sent fields are changed) |
| DELETE | `/api/customers/{id}` | Delete a customer |

**Create/Update body:**
```json
{
  "name": "Vic Adejumo",
  "email": "vic@test.com",
  "password": "pass123",
  "dietaryPreferences": ["vegan"]
}
```


## Recipe


| GET | `/api/recipes` | List all recipes |
| GET | `/api/recipes?tag={tag}` | List recipes filtered by tag (case-insensitive) |
| GET | `/api/recipes/{id}` | Get one recipe |
| POST | `/api/recipes` | Create a recipe |
| PUT | `/api/recipes/{id}` | Update a recipe (partial) |
| DELETE | `/api/recipes/{id}` | Delete a recipe |

**Create/Update body:**
```json
{
  "title": "Vegan Bowl",
  "ingredients": ["rice", "tofu"],
  "instructions": "mix it",
  "tags": ["vegan"],
  "price": 9.99
}
```


## Review

A customer must have an active Subscription to a recipe before reviewing it.


| GET | `/api/reviews` | List all reviews |
| GET | `/api/reviews?recipeId={id}` | List reviews for a recipe |
| GET | `/api/reviews?customerId={id}` | List reviews by a customer |
| GET | `/api/reviews/{id}` | Get one review |
| POST | `/api/reviews` | Create a review (requires an existing subscription) |
| PUT | `/api/reviews/{id}` | Update a review's rating/comment (partial) |
| DELETE | `/api/reviews/{id}` | Delete a review |

**Create body:**
```json
{
  "customer": { "customerId": 1 },
  "recipe": { "recipeId": 1 },
  "rating": 5,
  "comment": "Great recipe!"
}
```



## Subscription

Represents a customer subscribing to a recipe. Subscribing is idempotent — subscribing to the same recipe twice returns the existing subscription rather than creating a duplicate.

| GET | `/api/subscriptions` | List all subscriptions |
| GET | `/api/subscriptions?customerId={id}` | List subscriptions for a customer |
| GET | `/api/subscriptions/{id}` | Get one subscription |
| POST | `/api/subscriptions` | Subscribe a customer to a recipe |
| DELETE | `/api/subscriptions/{id}` | Cancel a subscription |

**Create body:**
```json
{
  "customer": { "customerId": 1 },
  "recipe": { "recipeId": 1 }
}
```

## Save

Lets a customer bookmark a recipe, separate from subscribing. 



| GET | `/api/saves` | List all saves |
| GET | `/api/saves?customerId={id}` | List saves for a customer |
| GET | `/api/saves/{id}` | Get one save |
| POST | `/api/saves` | Save a recipe for a customer |
| DELETE | `/api/saves/{id}` | Remove a save |

**Create body:**
```json
{
  "customer": { "customerId": 1 },
  "recipe": { "recipeId": 1 }
}
```
