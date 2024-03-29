package frc.robot;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.ManipulatorControl.MANIPPOS;

public class Auton1CoOp implements OpModeInterface
{
   private RobotContainer robot;

   private int step = 0;
   private Pose2d startPoint  = new Pose2d(Units.feetToMeters(0), Units.feetToMeters(0),new Rotation2d(Math.PI));
   private Pose2d Path1[] = {
                    new Pose2d(Units.feetToMeters(16), Units.feetToMeters(0),new Rotation2d(Math.PI)),
                    //new Pose2d(Units.feetToMeters(14), Units.feetToMeters(0),new Rotation2d(Math.PI)),
                    //new Pose2d(Units.feetToMeters(15), Units.feetToMeters(0),new Rotation2d(0)),
                    //new Pose2d(Units.feetToMeters(15), Units.feetToMeters(-1.5),new Rotation2d(0)),
                    //new Pose2d(Units.feetToMeters(6.75), Units.feetToMeters(-1.5),new Rotation2d(0))
                    new Pose2d(Units.feetToMeters(6.75), Units.feetToMeters(0),new Rotation2d(Math.PI))
                     };
    private SwervePath firstPath  = new SwervePath( Path1 );

   public Auton1CoOp()
   {
      
      robot = RobotContainer.getInstance();
   }
   public void Init()
   {
      // if (DriverStation.getAlliance() == Alliance.Red ) {
      //    System.out.println("RED");
      //    startPoint = SwervePath.transform(startPoint);
      //    firstPath.swapSides();

      //    //secondPath.swapSides();
      // }
      step = 0;
      robot.resetOdometry(startPoint);
      robot._manipulator.clawGrabCone();
      if( robot._manipulator.clawGetPose() > 8.0 )
      {
          robot._manipulator.setManipPos( MANIPPOS.TOP );
          robot._manipulator.periodic();
       }
}
   public void Periodic()
   { //System.out.println("Pitch " + robot._drive.gyro.getRoll() );
      switch( step )
      {
         case 0:
            if (robot._manipulator.atPosition() )
				{
				   robot._manipulator.clawRelease();
				   step++;
				}
             break;
		 case 1:
		    if( robot._manipulator.clawGetPose() < 12.0 )
               {
				   robot._manipulator.setManipPos(MANIPPOS.TRAVEL);
				   step++;
               }
              		 
			break;
         case 2:
            if ( robot._manipulator.atPosition() && firstPath.atDestination())
            {
               robot._drive.chargeStationAutoLevel();
               step++;
            }
            break;

         case 3:
            if (robot._drive.chargeStationAutoLevel())
            {
               robot._drive.lock();
               step++;
            }
            break;
         default:
            break;
      }
   }
}
