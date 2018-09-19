package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This class represents an implementation of a ray-tracer for rendering 3D
 * scenes.
 * 
 * @author Blaz Bagic
 */
public class RayCaster {

  /** Used for double comparison. */
  private static final double DELTA = 2E-4;

  /**
   * Method determines colors for each intersection of the ray tracer.
   * 
   * @param section
   *          intersection
   * @param scene
   *          scene
   * @param ray
   *          ray
   * @param colors
   *          colors in rgb
   */
  static void determineColorFor(RayIntersection section, Scene scene, Ray ray, double[] colors) {
    if (section == null) {
      colors = new double[] { 0, 0, 0 };
      return;
    }

    for (LightSource light : scene.getLights()) {
      Point3D lightPoint = light.getPoint();

      RayIntersection intersection = findClosestIntersection(scene,
          Ray.fromPoints(lightPoint, section.getPoint()));

      if (intersection == null
          || (DELTA + lightPoint.sub(intersection.getPoint()).norm() <= lightPoint
              .sub(section.getPoint()).norm())) {
        continue;
      }

      Point3D lightProjection = intersection.getNormal().scalarMultiply(
          lightPoint.sub(intersection.getPoint()).scalarProduct(intersection.getNormal()));

      double lightNorm = lightPoint.sub(intersection.getPoint()).normalize()
          .scalarProduct(intersection.getNormal());
      lightNorm = (lightNorm > 0) ? lightNorm : 0;

      colors[0] += light.getR() * intersection.getKdr() * lightNorm;
      colors[1] += light.getG() * intersection.getKdg() * lightNorm;
      colors[2] += light.getB() * intersection.getKdb() * lightNorm;

      double angle = lightProjection
          .add(lightProjection.negate().add(lightPoint.sub(intersection.getPoint()))
              .scalarMultiply(-1))
          .normalize().scalarProduct(ray.start.sub(intersection.getPoint()).normalize());
      if (angle < 0) {
        continue;
      }

      double multiplier = Math.pow(angle, intersection.getKrn());
      colors[0] += light.getR() * intersection.getKrr() * multiplier;
      colors[1] += light.getG() * intersection.getKrg() * multiplier;
      colors[2] += light.getB() * intersection.getKrb() * multiplier;
    }
  }

  /**
   * Returns the closest ray intersection to the ray.
   * 
   * @param scene
   *          scene
   * @param ray
   *          ray
   * @return the closest ray intersection to the ray
   */
  private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
    RayIntersection result = null;

    for (GraphicalObject grOb : scene.getObjects()) {
      RayIntersection inter = grOb.findClosestRayIntersection(ray);
      if (!(inter == null || (result != null && result.getDistance() < inter.getDistance()))) {
        result = inter;
      }
    }
    return result;
  }

  /**
   * Returns an implementation of {@link IRayTracerProducer}.
   * 
   * @return an implementation of {@link IRayTracerProducer}
   */
  private static IRayTracerProducer getIRayTracerProducer() {
    return new IRayTracerProducer() {
      @Override
      public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal,
          double vertical, int width, int height, long requestNuber,
          IRayTracerResultObserver observer) {

        System.out.println("Započinjem izračune...");
        short[] red = new short[width * height];
        short[] green = new short[width * height];
        short[] blue = new short[width * height];

        Point3D vuv = viewUp.normalize();
        Point3D og = eye.negate().sub(view.negate()).normalize();

        Point3D yAxis = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv))).normalize();
        Point3D xAxis = og.vectorProduct(yAxis).normalize();
        // z-axis not needed because of the view-up vector

        Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.0))
            .add(yAxis.scalarMultiply(vertical / 2.0));

        Scene scene = RayTracerViewer.createPredefinedScene();

        short[] rgb = new short[3];
        int offset = 0;
        for (int y = 0; y < height; y++) {
          for (int x = 0; x < width; x++) {
            Point3D partX = xAxis.scalarMultiply(horizontal * x / (width - 1.0));
            Point3D partY = yAxis.scalarMultiply(vertical * y / (height - 1.0));
            Point3D screenPoint = screenCorner.add(partX).sub(partY);

            tracer(scene, Ray.fromPoints(eye, screenPoint), rgb);
            red[offset] = rgb[0] > 255 ? 255 : rgb[0];
            green[offset] = rgb[1] > 255 ? 255 : rgb[1];
            blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
            offset++;
          }
        }

        System.out.println("Izračuni gotovi...");
        observer.acceptResult(red, green, blue, requestNuber);
        System.out.println("Dojava gotova...");
      }

    };
  }

  /**
   * Main program, renders an image produced with the ray tracer.
   * 
   * @param args
   *          unused here
   */
  public static void main(String[] args) {
    RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
        new Point3D(0, 0, 10), 20, 20);
  }

  /**
   * Traces a ray vector and sets the colors for it's intersections.
   * 
   * @param scene
   *          scene
   * @param ray
   *          ray
   * @param rgb
   *          colors
   */
  static void tracer(Scene scene, Ray ray, short[] rgb) {

    double[] colors = new double[] { 15, 15, 15 };

    determineColorFor(findClosestIntersection(scene, ray), scene, ray, colors);

    // ambient
    rgb[0] = (short) colors[0];
    rgb[1] = (short) colors[1];
    rgb[2] = (short) colors[2];
  }
}