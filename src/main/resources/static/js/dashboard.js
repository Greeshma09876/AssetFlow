
document.addEventListener("DOMContentLoaded", function () {

    // ==========================================
    // 1. DISPLAY CURRENT DATE
    // ==========================================

    const dateElement = document.getElementById("currentDate");

    if (dateElement) {
        dateElement.textContent = new Date().toLocaleDateString(
            "en-IN",
            {
                day: "2-digit",
                month: "short",
                year: "numeric"
            }
        );
    }


    // ==========================================
    // 2. READ ASSET COUNTS
    // ==========================================

    const getCount = (selector) => {
        const element = document.querySelector(selector + " h2");

        return element
            ? Number(element.textContent.trim()) || 0
            : 0;
    };

    const counts = [
        getCount(".available"),
        getCount(".assigned"),
        getCount(".repair"),
        getCount(".retired"),
        getCount(".lost")
    ];

    const totalAssets = getCount(".stat-card:nth-child(1)");


    // ==========================================
    // 3. CHART CENTRE TEXT PLUGIN
    // ==========================================

    const centerTextPlugin = {
        id: "centerText",

        afterDraw(chart) {

            const { ctx, chartArea } = chart;

            if (!chartArea) return;

            const centerX = (chartArea.left + chartArea.right) / 2;
            const centerY = (chartArea.top + chartArea.bottom) / 2;

            ctx.save();

            // Total number
            ctx.font = "800 32px Inter, Segoe UI, sans-serif";
            ctx.fillStyle = "#171923";
            ctx.textAlign = "center";
            ctx.textBaseline = "middle";

            ctx.fillText(totalAssets, centerX, centerY - 10);

            // Label
            ctx.font = "600 11px Inter, Segoe UI, sans-serif";
            ctx.fillStyle = "#89909e";

            ctx.fillText("TOTAL ASSETS", centerX, centerY + 17);

            ctx.restore();
        }
    };


    // ==========================================
    // 4. CREATE ASSET DISTRIBUTION CHART
    // ==========================================

    const canvas = document.getElementById("assetChart");

    if (canvas && window.Chart) {

        new Chart(canvas, {

            type: "doughnut",

            plugins: [centerTextPlugin],

            data: {

                labels: [
                    "Available",
                    "Assigned",
                    "Under Repair",
                    "Retired",
                    "Lost"
                ],

                datasets: [{

                    data: counts,

                    backgroundColor: [
                        "#159B72",
                        "#8064C8",
                        "#DF9632",
                        "#64748B",
                        "#DC5555"
                    ],

                    hoverBackgroundColor: [
                        "#0E805D",
                        "#6B4EB0",
                        "#C47B1E",
                        "#475569",
                        "#BD4141"
                    ],

                    borderColor: "#ffffff",

                    borderWidth: 4,

                    hoverBorderWidth: 4,

                    hoverOffset: 12,

                    borderRadius: 6,

                    spacing: 3
                }]
            },

            options: {

                responsive: true,

                maintainAspectRatio: false,

                cutout: "72%",

                animation: {

                    animateRotate: true,

                    animateScale: true,

                    duration: 1500,

                    easing: "easeOutQuart"
                },

                interaction: {
                    intersect: false
                },

                plugins: {

                    legend: {

                        position: "bottom",

                        labels: {

                            usePointStyle: true,

                            pointStyle: "circle",

                            padding: 20,

                            color: "#626978",

                            font: {
                                family: "Inter, Segoe UI, sans-serif",
                                size: 12,
                                weight: "600"
                            }
                        }
                    },

                    tooltip: {

                        backgroundColor: "#171923",

                        titleColor: "#ffffff",

                        bodyColor: "#e5e7eb",

                        padding: 13,

                        cornerRadius: 9,

                        displayColors: true,

                        boxPadding: 5,

                        callbacks: {

                            label: function (context) {

                                return " " +
                                    context.label +
                                    ": " +
                                    context.parsed +
                                    " assets";
                            }
                        }
                    }
                }
            }
        });
    }

});
