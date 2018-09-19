package hr.fer.zemris.java.raytracer.model;

/**
 * Model of a sphere in a 3D space.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Sphere extends GraphicalObject {

  /** Center of the sphere. */
  private Point3D center;
  /** Radius of this sphere */
  private double radius;
  /** Diffuse red coefficient */
  private double kdr;
  /** Diffuse green coefficient */
  private double kdg;
  /** Diffuse blue coefficient */
  private double kdb;
  /** Reflexive red coefficient */
  private double krr;
  /** Reflexive green coefficient */
  private double krg;
  /** Reflexive blue coefficient */
  private double krb;
  /** Reflexive intensity coefficient */
  private double krn;

  /** Default constructor initializing all given fields. */
  public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr,
      double krg, double krb, double krn) {
    this.center = center;
    this.radius = radius;
    this.kdr = kdr;
    this.kdg = kdg;
    this.kdb = kdb;
    this.krr = krr;
    this.krg = krg;
    this.krb = krb;
    this.krn = krn;
  }

  @Override
  public RayIntersection findClosestRayIntersection(Ray ray) {

    Point3D eye = ray.start;

    double a = ray.direction.scalarProduct(ray.direction);
    double b = ray.direction.scalarMultiply(2).scalarProduct(eye.sub(center));
    double c = center.scalarProduct(center) - 2 * eye.scalarProduct(center) - radius * radius
        + eye.scalarProduct(eye);

    double d = b * b - 4 * a * c;

    if (d < 0) {
      return null;
    }

    double distance = Math.sqrt(a) * (b + Math.sqrt(d)) / -2 * a;
    Point3D intersectionPoint = eye.add(ray.direction.scalarMultiply(distance));

    if (distance <= 0) {
      return null;
    }

    boolean outer = intersectionPoint.sub(eye).norm() > radius;

    return new RayIntersection(intersectionPoint, distance, outer) {

      @Override
      public Point3D getNormal() {
        return getPoint().sub(center).normalize();
      }

      @Override
      public double getKrr() {
        return krr;
      }

      @Override
      public double getKrn() {
        return krn;
      }

      @Override
      public double getKrg() {
        return krg;
      }

      @Override
      public double getKrb() {
        return krb;
      }

      @Override
      public double getKdr() {
        return kdr;
      }

      @Override
      public double getKdg() {
        return kdg;
      }

      @Override
      public double getKdb() {
        return kdb;
      }
    };
  }

}
