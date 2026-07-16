<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Subscriptions</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body class="dashboard-body">
    <#include "_navbar.ftlh">

    <div class="dashboard-container">
        <h3>My Subscriptions</h3>

        <div class="card-grid">
            <#list subscriptions as recipe>
                <div class="service-card" data-id="${recipe.id()?c}">
                    <div class="card-body">
                        <h4>${recipe.title()}</h4>
                        <p class="provider-name">by ${recipe.providerName()}</p>
                        <div class="card-tags">
                            <#list recipe.tags() as tag>
                                <span class="tag-pill small">${tag}</span>
                            </#list>
                        </div>
                        <#if recipe.reviewCount() gt 0>
                            <p class="rating">${recipe.averageRating()?string("0.0")} (${recipe.reviewCount()} reviews)</p>
                        <#else>
                            <p class="rating">No reviews yet</p>
                        </#if>
                        <#if (recipe.ingredients()![])?size gt 0>
                            <p class="mealkit-list-label">Ingredients:</p>
                            <ul class="mealkit-list">
                                <#list recipe.ingredients() as ingredient>
                                    <li>${ingredient}</li>
                                </#list>
                            </ul>
                        </#if>
                    </div>
                </div>
            </#list>
        </div>

        <#if subscriptions?size == 0>
            <p class="no-subscriptions">You haven't subscribed to any recipes yet. <a href="/customer/${customerId}/dashboard">Browse recipes</a></p>
        </#if>
    </div>
</body>
</html>
