/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.turret;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Turret extends Subsystem {

  private static Turret INSTANCE;

  public static Turret getTurret() {
    if (INSTANCE == null) {
        INSTANCE = new Turret();
    }
    return INSTANCE;
  }

  private static final int XAXIS_ID = 1;
  private static final int YAXIS_ID = 2;

  private CANSparkMax xAxis, yAxis;

  private CANPIDController xPID, yPID;
  public double xKP, xKI, xKD, xKF, yKP, yKI, yKD, yKF, maxInput, minInput;

  private double xCurrentRPM, yCurrentRPM = 0;

  public Turret() {
    setUpMotors();
    setUpPID();

    setXRPM(0);
    setYRPM(0);
  }

  public void setUpMotors() {
    xAxis = new CANSparkMax(XAXIS_ID, MotorType.kBrushless);
    yAxis = new CANSparkMax(YAXIS_ID, MotorType.kBrushless);

    xAxis.restoreFactoryDefaults();
    yAxis.restoreFactoryDefaults();

    xAxis.setSmartCurrentLimit(60);
    yAxis.setSmartCurrentLimit(60);
  }

  public void setUpPID() {
    xPID = xAxis.getPIDController();
    yPID = yAxis.getPIDController();

    xKP = 0;
    xKI = 0;
    xKD = 0;
    xKF = 0;

    yKP = 0;
    yKI = 0;
    yKD = 0;
    yKF = 0;

    xPID.setP(xKP);
    xPID.setI(xKI);
    xPID.setD(xKD);
    xPID.setFF(xKF);

    yPID.setP(yKP);
    yPID.setI(yKI);
    yPID.setD(yKD);
    yPID.setFF(yKF);

    xPID.setOutputRange(minInput, maxInput);
    yPID.setOutputRange(minInput, maxInput);
  }

  public void setXRPM(double rpm) {

    xCurrentRPM = rpm;

    xPID.setReference(xCurrentRPM, ControlType.kVelocity);
  }

  public void setYRPM(double rpm) {

    yCurrentRPM = rpm;

    yPID.setReference(yCurrentRPM, ControlType.kVelocity);
  }

  @Override
  public void periodic() {
      final double xP = SmartDashboard.getNumber("XP Gain", 0);
      final double xI = SmartDashboard.getNumber("XI Gain", 0);
      final double xD = SmartDashboard.getNumber("XD Gain", 0);
      final double xF = SmartDashboard.getNumber("X Feed Forward", 0);
      final double xRPM = SmartDashboard.getNumber("X Set RPM", 0);
      
      final double yP = SmartDashboard.getNumber("YP Gain", 0);
      final double yI = SmartDashboard.getNumber("YI Gain", 0);
      final double yD = SmartDashboard.getNumber("YD Gain", 0);
      final double yF = SmartDashboard.getNumber("Y Feed Forward", 0);
      final double yRPM = SmartDashboard.getNumber("Y Set RPM", 0);

      if (xP != xKP) {
          xPID.setP(xP);
          xKP = xP;
      }
      if (xI != xKI) {
          xPID.setI(xI);
          xKI = xI;
      }
      if (xD != xKD) {
          xPID.setD(xD);
          xKD = xD;
      }
      if (xF != xKF) {
          xPID.setFF(xF);
          xKF = xF;
      }
      if (xRPM != xCurrentRPM) {
          xPID.setReference(xRPM, ControlType.kVelocity);
          xCurrentRPM = xRPM;
      }

      if (yP != yKP) {
        yPID.setP(yP);
        yKP = yP;
      }
      if (yI != yKI) {
        yPID.setI(yI);
        yKI = yI;
      }
      if (yD != yKD) {
        yPID.setD(yD);
        yKD = yD;
      }
      if (yF != yKF) {
        yPID.setFF(yF);
        yKF = yF;
      }
      if (yRPM != yCurrentRPM) {
        yPID.setReference(yRPM, ControlType.kVelocity);
        yCurrentRPM = yRPM;
      }
  }

  @Override
  public void initDefaultCommand() {

  }
}
