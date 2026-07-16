<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Dashboard</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body class="dashboard-body">
    <#include "_navbar.ftlh">

    <div class="dashboard-container">
        <h3>Welcome back!!</h3>

        <div class="filter-tags">
            <span class="tag-pill active" data-filter="All">All</span>
            <#list recipes as recipe>
                <#list recipe.tags() as tag>
                    <span class="tag-pill" data-filter="${tag}">${tag}</span>
                </#list>
            </#list>
        </div>

        <div class="card-grid">
            <#list recipes as recipe>
                <div class="service-card" data-id="${recipe.id()?c}" data-tags="<#list recipe.tags() as tag>${tag}<#sep> </#sep></#list>">
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
                    </div>
                    <#if recipe.subscribed()>
                        <button class="subscribe-btn" disabled>Subscribed</button>
                    <#else>
                        <form method="post" action="/customer/${customerId}/subscribe/${recipe.id()?c}">
                            <button type="submit" class="subscribe-btn">Subscribe</button>
                        </form>
                    </#if>
                </div>
            </#list>
        </div>

        <#if recipes?size == 0>
            <p class="no-results">No recipes are available yet.</p>
        </#if>
        <p class="no-results" hidden>No recipes match this filter yet.</p>
    </div>

    <script>
        const filterTags = document.querySelectorAll('.filter-tags .tag-pill');
        const cards = document.querySelectorAll('.service-card');
        const noResults = document.querySelector('p.no-results[hidden]');

        filterTags.forEach(tag => {
            tag.addEventListener('click', () => {
                filterTags.forEach(t => t.classList.remove('active'));
                tag.classList.add('active');

                const filter = tag.dataset.filter;
                let visibleCount = 0;

                cards.forEach(card => {
                    const matches = filter === 'All' || card.dataset.tags.split(' ').includes(filter);
                    card.hidden = !matches;
                    if (matches) visibleCount++;
                });

                if (noResults) noResults.hidden = visibleCount > 0;
            });
        });
    </script>
</body>
</html>
