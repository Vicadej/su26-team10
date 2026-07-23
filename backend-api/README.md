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


## Provider

Providers are created through the UI, not the API — there is no POST endpoint.

| GET | `/api/providers` | List all providers |
| GET | `/api/providers/{id}` | Get one provider |
| GET | `/api/providers/email/{email}` | Get a provider by email |
| GET | `/api/providers/specialty?query={query}` | List providers matching a specialty |
| PUT | `/api/providers/{id}` | Update a provider |
| DELETE | `/api/providers/{id}` | Delete a provider |

**Update body:**
```json
{
  "name": "Chef Vic",
  "email": "chef@test.com",
  "password": "pass123",
  "bio": "Plant-based meal prep specialist",
  "specialties": "vegan",
  "certified": true
}
```


## Mealplan

A mealplan belongs to a provider. There is no DELETE endpoint.

| GET | `/api/mealplans` | List all mealplans |
| GET | `/api/mealplans/{id}` | Get one mealplan |
| GET | `/api/mealplans/provider/{providerId}` | List mealplans by a provider |
| GET | `/api/mealplans/category/{category}` | List mealplans in a category |
| GET | `/api/mealplans/search?query={query}` | Search mealplans by title |
| POST | `/api/mealplans` | Create a mealplan |
| PUT | `/api/mealplans/{id}` | Update a mealplan |

**Create/Update body:**
```json
{
  "provider": { "id": 1 },
  "title": "7-Day Vegan Plan",
  "duration": "7 days",
  "description": "A week of plant-based meals",
  "category": "vegan",
  "price": 49.99
}
```


## Mealkit

A mealkit belongs to a provider. There is no DELETE endpoint.

| GET | `/api/mealkits` | List all mealkits |
| GET | `/api/mealkits/{id}` | Get one mealkit |
| GET | `/api/mealkits/provider/{providerId}` | List mealkits by a provider |
| GET | `/api/mealkits/category/{category}` | List mealkits in a category |
| GET | `/api/mealkits/search?query={query}` | Search mealkits by title |
| POST | `/api/mealkits` | Create a mealkit |
| PUT | `/api/mealkits/{id}` | Update a mealkit |

**Create/Update body:**
```json
{
  "provider": { "id": 1 },
  "title": "Vegan Starter Kit",
  "deliveryFrequency": "weekly",
  "description": "Everything you need for a week of vegan meals",
  "category": "vegan",
  "price": 39.99
}
```
