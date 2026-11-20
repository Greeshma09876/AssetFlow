
document.addEventListener("DOMContentLoaded", function () {

    const searchInput = document.getElementById("historySearch");
    const table = document.getElementById("historyTable");
    const noResults = document.getElementById("historyNoResults");

    if (!searchInput || !table || !noResults) {
        return;
    }

    searchInput.addEventListener("input", function () {

        const searchValue = searchInput.value.toLowerCase().trim();
        const rows = table.querySelectorAll("tbody tr");

        let visibleCount = 0;

        rows.forEach(function (row) {

            if (row.querySelector(".empty-message")) {
                return;
            }

            const matches = row.textContent.toLowerCase().includes(searchValue);

            row.style.display = matches ? "" : "none";

            if (matches) {
                visibleCount++;
            }
        });

        noResults.hidden = visibleCount > 0;
    });
});
