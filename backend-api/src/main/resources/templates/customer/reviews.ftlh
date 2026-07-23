<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Reviews</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body class="dashboard-body">
    <#include "_navbar.ftlh">

    <div class="dashboard-container">
        <h3>My Reviews</h3>

        <div class="card-grid">
            <#list reviewCards as rc>
                <div class="service-card" data-id="${rc.recipe().id()?c}">
                    <div class="card-body">
                        <h4>${rc.recipe().title()}</h4>
                        <p class="provider-name">by ${rc.recipe().providerName()}</p>
                    </div>
                    <form class="review-form" method="post" action="/customer/${customerId}/reviews/${rc.recipe().id()?c}">
                        <label>Your Rating</label>
                        <select class="review-rating" name="rating">
                            <option value="5" <#if rc.existingRating()?? && rc.existingRating() == 5>selected</#if>>5 - Excellent</option>
                            <option value="4" <#if rc.existingRating()?? && rc.existingRating() == 4>selected</#if>>4 - Good</option>
                            <option value="3" <#if rc.existingRating()?? && rc.existingRating() == 3>selected</#if>>3 - Okay</option>
                            <option value="2" <#if rc.existingRating()?? && rc.existingRating() == 2>selected</#if>>2 - Poor</option>
                            <option value="1" <#if rc.existingRating()?? && rc.existingRating() == 1>selected</#if>>1 - Terrible</option>
                        </select>
                        <label>Your Review</label>
                        <textarea class="review-comment" name="comment" placeholder="What did you think?">${(rc.existingComment())!}</textarea>
                        <button type="submit" class="subscribe-btn review-save-btn">
                            <#if rc.existingRating()??>Update Review<#else>Save Review</#if>
                        </button>
                    </form>
                </div>
            </#list>
        </div>

        <#if reviewCards?size == 0>
            <p class="no-subscriptions">You haven't subscribed to any recipes yet. <a href="/customer/${customerId}/dashboard">Browse recipes</a></p>
        </#if>
    </div>
</body>
</html>
