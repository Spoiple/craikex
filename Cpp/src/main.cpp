#include <SFML/Window.hpp>
#include <SFML/Graphics.hpp>
#include <string>
#include <immintrin.h>
#include <chrono>
#include "loops/DOD.h"
#include "loops/OODCont.h"
#include "loops/OODPointer.h"

int main() {

    const int WINDOW_WIDTH = 640;
    const int WINDOW_HEIGHT = 480;
    const int NUMBER_OF_ENTITIES = 500;

    // todo disable when running tests
    sf::RenderWindow window(sf::VideoMode(WINDOW_WIDTH, WINDOW_HEIGHT), "My window");

//    auto *func = new exj::OODCont(&window, NUMBER_OF_ENTITIES, WINDOW_WIDTH, WINDOW_HEIGHT);
//    auto *func = new exj::OODPointer(&window, NUMBER_OF_ENTITIES, WINDOW_WIDTH, WINDOW_HEIGHT);
    auto *func = new exj::DOD(&window, NUMBER_OF_ENTITIES, WINDOW_WIDTH, WINDOW_HEIGHT);

    auto time = std::chrono::system_clock::now().time_since_epoch().count();
//    auto startTime = std::chrono::system_clock::now().time_since_epoch().count();
//    int nbFrames = 0;
//    while (std::chrono::system_clock::now().time_since_epoch().count() - startTime < 60'000'000'000) {
//    while (true) {
    while (window.isOpen()) {
        auto frameTimer = std::chrono::system_clock::now().time_since_epoch().count() - time;
        time = std::chrono::system_clock::now().time_since_epoch().count();
        sf::Event event{};
        while (window.pollEvent(event)) {
            // Close window: exit
            if (event.type == sf::Event::Closed)
                window.close();

            // Escape key: exit
            if ((event.type == sf::Event::KeyPressed) && (event.key.code == sf::Keyboard::Escape))
                window.close();
        }
//        nbFrames++;
        func->update(frameTimer/1'000'000'000.0);

        // todo disable when testing
        func->render();
    }

//    printf("frames: %d time: %d", nbFrames, (std::chrono::system_clock::now().time_since_epoch().count() - startTime) / 100'000);

    return EXIT_SUCCESS;
}