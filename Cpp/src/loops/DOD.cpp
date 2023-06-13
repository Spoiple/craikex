#include "DOD.h"

namespace exj {
    DOD::DOD(sf::RenderWindow *window, int numberOfEntities, int width, int height) {
        this->width = width;
        this->height = height;
        this->numberOfEntities = numberOfEntities;
        positions = new exj::Position[numberOfEntities];
        velocities = new exj::Velocity[numberOfEntities];
        collisions = new exj::Collision[numberOfEntities];
        sizes = new exj::Size[numberOfEntities];
        colors = new exj::Color[numberOfEntities];
        vertexArray = sf::VertexArray(sf::Quads);
        vertexArray.resize(numberOfEntities * 4);
        this->window = window;
        init();
    }

    void DOD::init() {
        for (int i = 0; i < numberOfEntities; i++) {
            colors[i].r = (float) rand()/RAND_MAX;
            colors[i].g = (float) rand()/RAND_MAX;
            colors[i].b = (float) rand()/RAND_MAX;
            positions[i].x = rand() % (int) width;
            positions[i].y = rand() % (int) height;
            sizes[i].width = sizes[i].height = 10;
            velocities[i].x = (rand() % 160) - 80;
            velocities[i].y = (rand() % 160) - 80;
        }
    }

    void DOD::update(float dt) {
        movementSystem(dt);
        collisionSystem();
        collisionHandlingSystem();
    }

    void DOD::movementSystem(float dt) {
        for (int i = 0; i < numberOfEntities; i++) {
            positions[i].x += velocities[i].x * (dt);
            positions[i].y += velocities[i].y * (dt);
        }
    }

    void DOD::collisionSystem() {
        for (int i = 0; i < numberOfEntities; i++) {
            if (positions[i].x < 0) {
                positions[i].x = 0;
                velocities[i].x = -velocities[i].x;
                collisions[i].hasCollided = true;
            } else if (positions[i].x > width - sizes[i].width) {
                positions[i].x = width - sizes[i].width;
                velocities[i].x = -velocities[i].x;
                collisions[i].hasCollided = true;
            }
            if (positions[i].y < 0) {
                positions[i].y = 0;
                velocities[i].y = -velocities[i].y;
                collisions[i].hasCollided = true;
            } else if (positions[i].y > height - sizes[i].height) {
                positions[i].y = height - sizes[i].height;
                velocities[i].y = -velocities[i].y;
                collisions[i].hasCollided = true;
            }
        }
    }

    void DOD::collisionHandlingSystem() {
        for (int i = 0; i < numberOfEntities; i++) {
            if (collisions[i].hasCollided) {
                collisions[i].hasCollided = false;
                colors[i].r = (float) rand()/RAND_MAX;
                colors[i].g = (float) rand()/RAND_MAX;
                colors[i].b = (float) rand()/RAND_MAX;
            }
        }
    }

    void DOD::render() {
        for (int i = 0; i < numberOfEntities; i++) {
            sf::Vertex *quad = &vertexArray[i * 4];
            quad[0].position = sf::Vector2f(positions[i].x, positions[i].y);
            quad[1].position = sf::Vector2f(positions[i].x + sizes[i].width, positions[i].y);
            quad[2].position = sf::Vector2f(positions[i].x + sizes[i].width, positions[i].y + sizes[i].height);
            quad[3].position = sf::Vector2f(positions[i].x, positions[i].y + sizes[i].height);

            quad[0].color = quad[1].color = quad[2].color = quad[3].color = {static_cast<sf::Uint8>(0xFF * colors[i].r), static_cast<sf::Uint8>(0xFF * colors[i].g), static_cast<sf::Uint8>(0xFF * colors[i].b)};
        }
        window->clear();
        window->draw(vertexArray);
        window->display();
    }


} // exj