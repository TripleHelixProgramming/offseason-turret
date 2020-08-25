/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.limelight;

import static edu.wpi.first.networktables.NetworkTableInstance.getDefault;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Limelight extends Subsystem {
  
  private static Limelight INSTANCE = null;

  static double cameraHeight = 21.25; // (inches) currently the height on the programming bot
  static double bottomTargetHeight = 81; // (inches) use 81.25 for actual arena height
  static double cameraElevation = 26.5; // (degrees) currently the angle on the programming bot (was 27.75)

  private String name;

  public Limelight(String name) {
    this.name = name;
  }

  public static Limelight getLimelight() {
    if (INSTANCE == null) INSTANCE = new Limelight("limelight-front");
    return INSTANCE;
  }

  enum Lights {
    PIPELINE, OFF, BLINK, ON;
  }

  public void setLEDMode(Lights lights) {
    int ledMode;

    switch (lights) {
      case PIPELINE:
        ledMode = 0;
        break;
      case OFF:
        ledMode = 1;
        break;
      case BLINK:
        ledMode = 2;
        break;
      case ON:
        ledMode = 3;
        break;
      default:
        ledMode = 1;
        // log bad value
        break;
    }

    getDefault().getTable(name).getEntry("ledMode").setNumber(ledMode);
  }

  public int getLEDMode() {
    return (int)getDefault().getTable(name).getEntry("ledMode").getDouble(0);
  }

  public void setPipeline(int pipeline) {
    getDefault().getTable(name).getEntry("pipeline").setNumber(pipeline);
  }

  public int getPipeline() {
    return (int)getDefault().getTable(name).getEntry("pipeline").getDouble(0);
  }

  public double getXOffset() {
    return getDefault().getTable(name).getEntry("tx").getDouble(0);
  }

  public double getYOffset() {
    return getDefault().getTable(name).getEntry("ty").getDouble(0);
  }

  public double getArea() {
    return getDefault().getTable(name).getEntry("ty").getDouble(0);
  }

  public boolean getTarget() {
    return getDefault().getTable(name).getEntry("tv").getDouble(0) == 1;
  }

  public void setDriverMode() {
    setLEDMode(Lights.OFF);
    setPipeline(0);
  }

  public void setAimingMode() {
    setLEDMode(Lights.ON);
    setPipeline(1);
  }

  public double getDistanceToTarget() {
    return (bottomTargetHeight - cameraHeight) / Math.tan(Math.toRadians(cameraElevation + getYOffset()));
    // calculates ground distane from robot to target, only accurate when tx = 0
  }

  public double getRPM() {

    // double calcRPMNum = Math.pow(calculateDistanceToTarget(),2)*g;
    // double calcRPMDenFirstTerm = calculateDistanceToTarget()*Math.sin(Math.toRadians(2*shooterElevation));
    // double calcRPMDenSecondTerm = 2*h*Math.pow(Math.cos(Math.toRadians(shooterElevation)), 2);
    // double linearVelocity = Math.sqrt((calcRPMNum)/(calcRPMDenFirstTerm-calcRPMDenSecondTerm));
    
    // return (int)((linearVelocity*60)/(2*Math.PI*r));
    // if (determineHoodPostion() == 1)
    //     return 39.5 * (calculateDistanceToTarget() / 12) + 1987;
    // return 39.5 * (calculateDistanceToTarget() / 12) + 1987;
    // return 39.5 * (calculateDistanceToTarget() / 12.0) + 1987.0; BOT 2
    return 21.8 * (getDistanceToTarget() / 12.0) + 2486.4;
}

  @Override
  public void initDefaultCommand() {

  }
}
