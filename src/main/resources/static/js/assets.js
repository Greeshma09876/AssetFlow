
document.addEventListener("DOMContentLoaded", function () {

    const searchInput = document.getElementById("assetSearch");
    const statusFilter = document.getElementById("statusFilter");
    const table = document.getElementById("assetTable");
    const noResults = document.getElementById("noSearchResults");

    function filterAssets() {

        const searchText = searchInput.value.toLowerCase().trim();
        const selectedStatus = statusFilter.value.toLowerCase();

        const rows = table.querySelectorAll("tbody tr");
        let visibleCount = 0;

        rows.forEach(function (row) {

            if (row.querySelector(".empty-message")) {
                return;
            }

            const rowText = row.textContent.toLowerCase();

            const statusElement = row.querySelector(".status-badge");

            const rowStatus = statusElement
                ? statusElement.textContent.trim().toLowerCase()
                : "";

            const matchesSearch = rowText.includes(searchText);

            const matchesStatus =
                selectedStatus === "" ||
                rowStatus === selectedStatus;

            if (matchesSearch && matchesStatus) {
                row.style.display = "";
                visibleCount++;
            } else {
                row.style.display = "none";
            }
        });

        noResults.hidden = visibleCount > 0;
    }

    searchInput.addEventListener("input", filterAssets);
    statusFilter.addEventListener("change", filterAssets);

});
