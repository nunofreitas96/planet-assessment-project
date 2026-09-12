pluginManagement {
    plugins {
        kotlin("plugin.jpa") version "2.4.10"
    }
}
rootProject.name = "PlanetAssessment"

include(
    "adapters",
    "application",
    "domain",
    "planet-assessment-api"
)