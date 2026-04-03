import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;
import java.util.Map;

public class Particle {
    public ParticleFlavor flavor;
    public int lifespan;

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                   ParticleFlavor.PLANT, PLANT_LIFESPAN,
                   ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public Particle(ParticleFlavor flavor) {
        this.flavor = flavor;
        if (flavor == ParticleFlavor.PLANT || flavor == ParticleFlavor.FIRE || flavor == ParticleFlavor.FLOWER) {
            lifespan = LIFESPANS.get(flavor);
        } else lifespan = -1;
    }
    // flavor: 风味；特点；特色
    // cyan: 青色，蓝绿色
    public Color color() {
        if (flavor == ParticleFlavor.EMPTY) {
            return Color.BLACK;
        } else if (flavor == ParticleFlavor.SAND) {
            return Color.YELLOW;
        } else if (flavor == ParticleFlavor.BARRIER) {
            return Color.GRAY;
        } else if (flavor == ParticleFlavor.WATER) {
            return Color.BLUE;
        } else if (flavor == ParticleFlavor.FOUNTAIN) {
            return Color.CYAN;
        }
//        else if (flavor == ParticleFlavor.PLANT) {
//            return new Color(0, 255, 0);
//        } else if (flavor == ParticleFlavor.FIRE) {
//            return new Color(255, 0, 0);
//        } else if (flavor == ParticleFlavor.FLOWER) {
//            return new Color(255, 141, 161);
//        }
        if (flavor == ParticleFlavor.FLOWER) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FLOWER_LIFESPAN)) / FLOWER_LIFESPAN;
            int r = 120 + (int) Math.round((255 - 120) * ratio);
            int g = 70 + (int) Math.round((141 - 70) * ratio);
            int b = 80 + (int) Math.round((161 - 80) * ratio);
            return new Color(r, g, b);
        }
        if (flavor == ParticleFlavor.PLANT) {
            double ratio = (double) Math.max(0, Math.min(lifespan, PLANT_LIFESPAN)) / PLANT_LIFESPAN;
            int g = 120 + (int) Math.round((255 - 120) * ratio);
            return new Color(0, g, 0);
        }
        if (flavor == ParticleFlavor.FIRE) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FIRE_LIFESPAN)) / FIRE_LIFESPAN;
            int r = (int) Math.round(255 * ratio);
            return new Color(r, 0, 0);
        }

        return Color.GRAY;
    }

    public void moveInto(Particle other) {
        other.flavor = flavor;
        other.lifespan = lifespan;
        flavor = ParticleFlavor.EMPTY;
        lifespan = -1;
    }

    public void fall(Map<Direction, Particle> neighbors) {
        Particle p = neighbors.get(Direction.DOWN);
        if (p.flavor == ParticleFlavor.EMPTY) {
            moveInto(p);
        }
    }

    public void flow(Map<Direction, Particle> neighbors) {
        int rand = StdRandom.uniformInt(3);
        Particle p_left = neighbors.get(Direction.LEFT);
        Particle p_right = neighbors.get(Direction.RIGHT);
        if (rand == 1 & p_left.flavor == ParticleFlavor.EMPTY) moveInto(p_left);
        if (rand == 2 & p_right.flavor == ParticleFlavor.EMPTY) moveInto(p_right);
    }

    public void grow(Map<Direction, Particle> neighbors) {
        int rand = StdRandom.uniformInt(10);
        Particle p_left = neighbors.get(Direction.LEFT);
        Particle p_right = neighbors.get(Direction.RIGHT);
        Particle p_up = neighbors.get(Direction.UP);
        if (rand == 0 & p_left.flavor == ParticleFlavor.EMPTY) {
            p_left.flavor = flavor;
            p_left.lifespan = LIFESPANS.get(flavor);
        }
        if (rand == 1 & p_right.flavor == ParticleFlavor.EMPTY) {
            p_right.flavor = flavor;
            p_right.lifespan = LIFESPANS.get(flavor);
        }
        if (rand == 2 & p_up.flavor == ParticleFlavor.EMPTY) {
            p_up.flavor = flavor;
            p_up.lifespan = LIFESPANS.get(flavor);
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
        int rand0 = StdRandom.uniformInt(10);
        int rand1 = StdRandom.uniformInt(10);
        int rand2 = StdRandom.uniformInt(10);
        int rand3 = StdRandom.uniformInt(10);
        Particle p_left = neighbors.get(Direction.LEFT);
        Particle p_right = neighbors.get(Direction.RIGHT);
        Particle p_up = neighbors.get(Direction.UP);
        Particle p_down = neighbors.get(Direction.DOWN);
        if (rand0 < 4) {
            if (p_up.flavor == ParticleFlavor.FLOWER || p_up.flavor == ParticleFlavor.PLANT) {
                p_up.flavor = ParticleFlavor.FIRE;
                p_up.lifespan = LIFESPANS.get(ParticleFlavor.FIRE);
            }
        } if (rand1 < 4) {
            if (p_down.flavor == ParticleFlavor.FLOWER || p_down.flavor == ParticleFlavor.PLANT) {
                p_down.flavor = ParticleFlavor.FIRE;
                p_down.lifespan = LIFESPANS.get(ParticleFlavor.FIRE);
            }
        } if (rand2 < 4) {
            if (p_right.flavor == ParticleFlavor.FLOWER || p_right.flavor == ParticleFlavor.PLANT) {
                p_right.flavor = ParticleFlavor.FIRE;
                p_right.lifespan = LIFESPANS.get(ParticleFlavor.FIRE);
            }
        } if (rand3 < 4) {
            if (p_left.flavor == ParticleFlavor.FLOWER || p_left.flavor == ParticleFlavor.PLANT) {
                p_left.flavor = ParticleFlavor.FIRE;
                p_left.lifespan = LIFESPANS.get(ParticleFlavor.FIRE);
            }
        }
    }

    public void action(Map<Direction, Particle> neighbors) {
        if (flavor == ParticleFlavor.EMPTY) {
            return;
        } if (flavor != ParticleFlavor.BARRIER) {
            fall(neighbors);
        } if (flavor == ParticleFlavor.WATER) {
            flow(neighbors);
        } if (flavor == ParticleFlavor.PLANT || flavor == ParticleFlavor.FLOWER) {
            grow(neighbors);
        } if (flavor == ParticleFlavor.FIRE) {
            burn(neighbors);
        }
    }

    public void decrementLifespan() {
        if (lifespan > 0) lifespan -= 1;
        if (lifespan == 0) {
            flavor = ParticleFlavor.EMPTY;
            lifespan = -1;
        }
    }
}