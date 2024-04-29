# Maestro
[Maestro](https://maestro.mobile.dev/) is the simplest and most effective mobile UI testing framework, is a modern, straightforward, and highly 
efficient solution designed for rapid mobile UI testing.

This project includes 2 test flows:
- [main_screens_test](main_screens_test.yaml): This flow tries validate that the `Home` screen has content, then 
  navigates to `Favorites` and validates that there are no favorites saved and then moves again to `Home` screen and 
  clicks on the first item on the list and validates that the title `Dimensions` is present in the `Detail` screen.
- [artwork_favorites_test](artwork_favorites_test.yaml): This flow saves the first artwork from the `Home` screen in 
  to `Favorites`, then moves to the `Favorites` screen and validates that there's one artwork saved.

To run Maestro you need to install the CLI first, check [here](https://maestro.mobile.dev/getting-started/installing-maestro), once you installed, 
you need to first run the app in the emulator, then you just need to tun the command `maestro test maestro_tests/` 
to run both test or `maestro test maestro_tests/TEST_FLOW_TO_RUN` to run one specific flow.

## 🎥 Test sample video


https://github.com/alejandro-rios/Art-Catalog-App/assets/10689052/46fc1f94-408e-4061-9970-1ef80f29302a
