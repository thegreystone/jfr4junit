# jfr4junit
Quick hack for JFR integration for JUnit5. Built during Hackgarten Luzern 2016-05-05.

# Usage
Put @ExtendWith(JFRJUnit5Extension.class) for test classes where you want JFR events.

# Command Line Example
Example VM arguments: -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:FlightRecorderOptions=defaultrecording=true,dumponexit=true,dumponexitpath=c:\demo\dumponexitjunit5.jfr -XX:StartFlightRecording=name=JUnitSettings,settings=c:\demo\junit5.jfc

For example:
java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:FlightRecorderOptions=defaultrecording=true,dumponexit=true,dumponexitpath=c:\demo\dumponexitjunit5.jfr -XX:StartFlightRecording=name=JUnitSettings,settings=c:\demo\junit5.jfc org.junit.gen5.console.ConsoleRunner -a -p C:\Users\marcus.APPEAL.SE\git\jfr4junit\se.hirt.jfr4junit\test
