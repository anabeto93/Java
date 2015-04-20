package TrafficLight;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 *
 * @authors Africana and Anabeto
 */
public class Intersection extends AppSim
// build the road and frame
{

	int frameX, frameY;
	// *Road References Variables
	protected static int road, laneSize;
	protected static int sideWalk, grassPatch, arc;
	protected static int roadNSXLoc, roadNSYLoc, roadEWXLoc, roadEWYLoc;
	protected static int roadNSCen, roadEWCen;
	protected static int stopBandLNW, stopBandLSE, stopBandRNW, stopBandRSE;
	protected static int intersectionSE, intersectionNW;

	int lightLength, lightWidth;
	public volatile boolean[] canMove; // a car can either move or stop
	public volatile boolean[] canRoll; // rolling is

	// Container pro = getContentPane();

	Intersection() {
		frameX = FrameX;
		frameY = FrameY;

		road = 180; // loses 1px to grassPatch
		laneSize = road / 4;// 75px
		sideWalk = 15;
		grassPatch = (frameX - road) / 2; // 212.5px
		arc = 50;

		roadNSCen = frameX / 2; // north south center
		roadEWCen = frameY / 2; // east west center
		intersectionSE = grassPatch + road + sideWalk; // south east
														// intersection
		intersectionNW = grassPatch - sideWalk; // north west intersection
		sideLights = new TrafficLight[4];
		roadLights = new TrafficLight[4];

		for (int l = 0; l < roadLights.length; l++) {
			sideLights[l] = new TrafficLight();
			roadLights[l] = new TrafficLight();
		}
		canMove = new boolean[4]; // North is in sync with South, -- they move
									// together
		canRoll = new boolean[4]; // Yellow lights is just a warning to speed up
		canMove[0] = false;//true; // North is automatically set to start moving

		lightLength = 37;
		lightWidth = 11;
	}// end constructor

	public void drawIntersection(Graphics2D marker) {
		float stroke = 1.0f;
		int shiftKey = 7;

		// *North South
		roadNSXLoc = roadNSCen - (road / 2);
		roadNSYLoc = 0;
		drawRoad(marker, roadNSXLoc, roadNSYLoc, road, frameY);

		// *Zebra(NS)
		zCrossIn(marker, shiftKey);

		// *East West
		roadEWYLoc = roadEWCen - (road / 2);
		roadEWXLoc = 0;
		drawRoad(marker, roadEWXLoc, roadEWYLoc, frameX, road);

		drawNSEWLaneDivs(marker, stroke, shiftKey);
		drawNSEWLanes(marker);

		// *CrossWalk(EW)
		crossWalk(marker, (grassPatch - (sideWalk - shiftKey)), grassPatch,
				sideWalk, road);// West
		crossWalk(marker, (grassPatch + (road - shiftKey)), grassPatch,
				sideWalk, road);// East

		// *NSEW StopBands
		// stopBandsNSEW(marker);

		// *Off Road Grass Patch
		drawGrassPatch(marker, -shiftKey, -shiftKey, grassPatch + shiftKey,
				grassPatch + shiftKey);// topLeft Patch
		drawGrassPatch(marker, (grassPatch + road), -shiftKey, grassPatch
				+ shiftKey, grassPatch + shiftKey);// topRight Patch
		drawGrassPatch(marker, -shiftKey, (grassPatch + road), grassPatch
				+ shiftKey, grassPatch + shiftKey);// bottom Left
		drawGrassPatch(marker, (grassPatch + road), (grassPatch + road),
				grassPatch + shiftKey, grassPatch + shiftKey);// last

		// *Traffic Lights
		patchLights(marker);
		// streetLights(marker);

	}// end drawIntersection

	public void drawRoad(Graphics2D sticker, int rdLocX, int rdLocY,
			int rdXDim, int rdYDim) {
		// sticker.setColor(new Color(0,41,35));
		sticker.setColor(new Color(35, 41, 42)); // kind of a gray color of
													// sorts
		sticker.fillRect(rdLocX, rdLocY, rdXDim, rdYDim);
	}

	public void crossWalk(Graphics2D ink, int xLoc, int yLoc, int across,
			int over) {
		ink.setColor(Color.WHITE);
		ink.draw(new Rectangle2D.Double(xLoc, yLoc, across, over));
	}

	public void zCrossIn(Graphics2D ink, int shift) {
		ink.setColor(Color.WHITE);
		float zebra = road / 22;
		int stop = (grassPatch + road);
		for (int z = (grassPatch + shift + 2); z < stop; z++) {
			ink.fill(new Rectangle2D.Double(z, (grassPatch - sideWalk + shift),
					zebra, sideWalk));
			ink.fill(new Rectangle2D.Double(z, (grassPatch + road - shift),
					zebra, sideWalk));
			z += (zebra * 2);
		}
	}

	void stopBandsNSEW(Graphics2D ink) {
		ink.setColor(Color.WHITE);
		int bandWidth = 7;
		stopBandLNW = intersectionNW - bandWidth;
		stopBandRNW = stopBandLNW - (bandWidth / 3);
		stopBandLSE = intersectionSE + bandWidth;
		stopBandRSE = stopBandLSE + (bandWidth / 3);
		// *North
		ink.fill(new Rectangle2D.Double(grassPatch, stopBandLNW, laneSize,
				bandWidth));
		ink.fill(new Rectangle2D.Double(roadNSCen - laneSize, stopBandRNW,
				laneSize, bandWidth));
		// *West
		ink.fill(new Rectangle2D.Double(stopBandLNW, roadEWCen, bandWidth,
				laneSize - 7));
		ink.fill(new Rectangle2D.Double(stopBandRNW, roadEWCen + laneSize,
				bandWidth, laneSize - 7));
		// *South
		ink.fill(new Rectangle2D.Double(roadNSCen, stopBandLSE, laneSize,
				bandWidth));
		ink.fill(new Rectangle2D.Double(roadNSCen + laneSize, stopBandRSE,
				laneSize, bandWidth));
		// *East
		ink.fill(new Rectangle2D.Double(stopBandLSE, grassPatch, bandWidth,
				laneSize - 7));
		ink.fill(new Rectangle2D.Double(stopBandRSE, roadEWCen - laneSize,
				bandWidth, laneSize - 7));
	}

	void drawNSEWLanes(Graphics2D ink) // draw all the lanes
	{
		double markX = 3;
		double markY = 7;
		double shift = (markX / 2) + 3;
		ink.setColor(Color.WHITE);
		// *NS
		for (int d = -3; d < grassPatch - markX; d++) {
			ink.fill(new Rectangle2D.Double((roadNSXLoc + laneSize), d, markX,
					markY));// NorthLeft
			ink.fill(new Rectangle2D.Double((roadNSXLoc + laneSize), (d + road
					+ grassPatch + sideWalk), markX, markY));
			ink.fill(new Rectangle2D.Double(
					(roadNSXLoc + road - laneSize - shift), d, markX, markY));// NorthRight
			ink.fill(new Rectangle2D.Double(
					(roadNSXLoc + road - laneSize - shift), (d + road
							+ grassPatch + sideWalk), markX, markY));
			d += (markY * 2);
		}

		// *EW
		for (int d = -3; d < grassPatch - markX; d++) {
			ink.fill(new Rectangle2D.Double(d, (roadEWYLoc + laneSize), markY,
					markX));// EastLeft
			ink.fill(new Rectangle2D.Double((d + road + grassPatch + sideWalk),
					(roadEWYLoc + laneSize), markY, markX));
			ink.fill(new Rectangle2D.Double(d,
					(roadEWYLoc + road - laneSize - shift), markY, markX));// EastRight
			ink.fill(new Rectangle2D.Double((d + road + grassPatch + sideWalk),
					(roadEWYLoc + road - laneSize - shift), markY, markX));
			d += (markY * 2);
		}

	}

	public void drawGrassPatch(Graphics2D sticker, int grsLocX, int grsLocY,
			int grsPatchX, int grsPatchY) {
		sticker.setColor(new Color(110, 243, 82));
		sticker.fillRoundRect(grsLocX, grsLocY, grsPatchX, grsPatchY, arc, arc);
		sideWalk(sticker, grsLocX, grsLocY, grsPatchX, grsPatchY);
	}

	public void sideWalk(Graphics2D ink, double wlkLocX, double wlkLocY,
			double wlkX, double wlkY) {
		ink.setColor(new Color(189, 192, 185));
		BasicStroke sideWall = new BasicStroke(sideWalk, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);
		ink.setStroke(sideWall);
		ink.draw(new Rectangle2D.Double(wlkLocX, wlkLocY, wlkX, wlkY));
	}

	void drawNSEWLaneDivs(Graphics2D marker, float stroke, int shift) {
		final float GPPSWPRD = intersectionSE - shift;
		final float GPMSW = intersectionNW + shift;
		float ld1X = roadNSCen - (5 * stroke / 4);
		float ld2X = ld1X;
		float ld1Y = 0f;
		float ld2Y = GPMSW;

		laneDivider(marker, stroke, ld1X, ld1Y, ld2X, ld2Y); // North left
		ld1Y = GPPSWPRD;
		ld2Y = frameY;
		laneDivider(marker, stroke, ld1X, ld1Y, ld2X, ld2Y);// South left

		ld1X = roadNSCen + (5 * stroke / 4);
		ld2X = ld1X;
		laneDivider(marker, stroke, ld1X, ld1Y, ld2X, ld2Y); // South right
		ld1Y = 0f;
		ld2Y = GPMSW;
		laneDivider(marker, stroke, ld1X, ld1Y, ld2X, ld2Y); // North right

		ld1Y = roadEWCen - (5 * stroke / 4);
		ld2Y = ld1Y;
		ld1X = 0f;
		ld2X = GPMSW;
		laneDivider(marker, stroke, ld1X, ld1Y, ld2X, ld2Y); // West left
		ld1X = GPPSWPRD;
		ld2X = frameX;
		laneDivider(marker, stroke, ld1X, ld1Y, ld2X, ld2Y);// East left

		ld1Y = roadEWCen + (5 * stroke / 4);
		ld2Y = ld1Y;
		laneDivider(marker, stroke, ld1X, ld1Y, ld2X, ld2Y); // East right
		ld1X = 0f;
		ld2X = GPMSW;
		laneDivider(marker, stroke, ld1X, ld1Y, ld2X, ld2Y); // West right
	}

	public void laneDivider(Graphics2D ink, float stroke, float pt1X,
			float pt1Y, float pt2X, float pt2Y) {
		ink.setColor(Color.YELLOW);
		BasicStroke style = new BasicStroke(stroke, BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_BEVEL);
		ink.setStroke(style);
		Line2D.Float divLine = new Line2D.Float(pt1X, pt1Y, pt2X, pt2Y);
		ink.draw(divLine);
	}

	void patchLights(Graphics2D marker) {
		sideLights[0].station(marker, (grassPatch - laneSize),
				(grassPatch - lightLength), lightLength, lightWidth);
		sideLights[1].station(marker, (grassPatch - laneSize), (grassPatch
				+ sideWalk + road), lightLength, lightWidth);
		sideLights[2].station(marker, (grassPatch + sideWalk + road),
				(grassPatch - lightLength), lightLength, lightWidth);
		sideLights[3].station(marker, (grassPatch + sideWalk + road),
				(grassPatch + sideWalk + road), lightLength, lightWidth);
	}

	void streetLights(Graphics2D marker) {
		int align = lightWidth / 2;
		roadLights[0].station(marker, (roadNSCen - laneSize - align + 1),
				intersectionNW + sideWalk + align, lightWidth, lightLength);// *North
		roadLights[1].station(marker, (roadNSCen + laneSize - align - 2.3),
				intersectionSE - laneSize - align - 3.3, lightWidth,
				lightLength);// *South
		roadLights[2].station(marker, intersectionNW + sideWalk + 11,
				(roadEWCen + laneSize - align - 2.3), lightLength, lightWidth);// *West
		roadLights[3].station(marker, intersectionSE - laneSize
				- (lightWidth + 3.3), (roadEWCen - laneSize - align),
				lightLength, lightWidth);// *East
	}

	// getters for the canMove
	public boolean getInnerNSMove() {
		return canMove[0];
	}

	public boolean getOuterNSMove() {
		return canMove[2];// cars can turn right
	}

	public boolean getInnerNSRoll() {
		return canRoll[0];
	}

	public boolean getOuterNSRoll() {
		return canRoll[2];
	}

	public boolean getInnerEWMove() {
		return canMove[1];
	}

	public boolean getOuterEWMove() {
		return canMove[3];
	}

	public boolean getInnerEWRoll() {
		return canRoll[1];
	}

	public boolean getOuterEWRoll() {
		return canRoll[3];
	}

	// setters for canMove
	public void innerNSMove() {
		for (int i = 1; i < 4; ++i) {// all other cars cannot move
			canMove[i] = false;
		}
		for (int i = 0; i < 4; i++) {
			sideLights[i].dimGreen();
			sideLights[i].dimYellow();
			sideLights[i].activeRed();
			roadLights[i].dimGreen();
			roadLights[i].dimYellow();
			roadLights[i].activeRed();
		}
		canMove[0] = true;
		roadLights[0].activeGreen();
		roadLights[0].dimYellow();
		roadLights[0].dimRed();
		roadLights[1].activeGreen();
		roadLights[1].dimYellow();
		roadLights[1].dimRed();
		
	}

	public void outerNSMove() {
		for (int i = 1; i < 4; ++i) {// all other cars cannot move
			canMove[i] = false;
		}
		for (int i = 0; i < 4; i++) {
			sideLights[i].dimGreen();
			sideLights[i].dimYellow();
			sideLights[i].activeRed();
			roadLights[i].dimGreen();
			roadLights[i].dimYellow();
			roadLights[i].activeRed();
		}
		canMove[2] = true;
			sideLights[0].activeGreen();
			sideLights[0].dimYellow();
			sideLights[0].dimRed();
			sideLights[3].activeGreen();
			sideLights[3].dimYellow();
			sideLights[3].dimRed();
	}

	public void innerEWMove() 
	{
		for (int i = 1; i < 4; ++i) {// all other cars cannot move
			canMove[i] = false;
		}
		for (int i = 0; i < 4; i++) {
			sideLights[i].dimGreen();
			sideLights[i].dimYellow();
			sideLights[i].activeRed();
			roadLights[i].dimGreen();
			roadLights[i].dimYellow();
			roadLights[i].activeRed();
		}
		canMove[1] = true;
		roadLights[2].activeGreen();
		roadLights[2].dimYellow();
		roadLights[2].dimRed();
		roadLights[3].activeGreen();
		roadLights[3].dimYellow();
		roadLights[3].dimRed();
	}

	public void outerEWMove() 
	{
		for (int i = 1; i < 4; ++i) {// all other cars cannot move
			canMove[i] = false;
		}
		for (int i = 0; i < 4; i++) {
			sideLights[i].dimGreen();
			sideLights[i].dimYellow();
			sideLights[i].activeRed();
			roadLights[i].dimGreen();
			roadLights[i].dimYellow();
			roadLights[i].activeRed();
		}
		canMove[3] = true;
			sideLights[1].activeGreen();
			sideLights[1].dimYellow();
			sideLights[1].dimRed();
			sideLights[2].activeGreen();
			sideLights[2].dimYellow();
			sideLights[2].dimRed();
	}

	public void innerNSRoll()
	{
		for (int i = 1; i < 4; ++i) {// all other cars cannot move
			canMove[i] = false;
		}
		for (int i = 0; i < 4; i++) {
			sideLights[i].dimGreen();
			sideLights[i].dimYellow();
			sideLights[i].activeRed();
			roadLights[i].dimGreen();
			roadLights[i].dimYellow();
			roadLights[i].activeRed();
		}
		canRoll[0] = true;
			for(int i=0; i<2; i++)
			{
				roadLights[i].dimGreen();
				roadLights[i].activeYellow();
				roadLights[i].dimRed();
			}
	}

	public void outerNSRoll() 
	{
		for (int i = 1; i < 4; ++i) {// all other cars cannot move
			canMove[i] = false;
		}
		for (int i = 0; i < 4; i++) {
			sideLights[i].dimGreen();
			sideLights[i].dimYellow();
			sideLights[i].activeRed();
			roadLights[i].dimGreen();
			roadLights[i].dimYellow();
			roadLights[i].activeRed();
		}
		canRoll[2] = true;
			for(int i=0; i<4; i++)
			{
				if(i==1 || i==2)
					continue;
				sideLights[i].dimGreen();
				sideLights[i].activeYellow();
				sideLights[i].dimRed();
			}
	}

	public void innerEWRoll() 
	{
		for (int i = 1; i < 4; ++i) {// all other cars cannot move
			canMove[i] = false;
		}
		for (int i = 0; i < 4; i++) {
			sideLights[i].dimGreen();
			sideLights[i].dimYellow();
			sideLights[i].activeRed();
			roadLights[i].dimGreen();
			roadLights[i].dimYellow();
			roadLights[i].activeRed();
		}
		canRoll[1] = true;
			for(int i=2; i<4; i++)
			{
				roadLights[i].dimGreen();
				roadLights[i].activeYellow();
				roadLights[i].dimRed();
			}
	}

	public void outerEWRoll() 
	{
		for (int i = 1; i < 4; ++i) {// all other cars cannot move
			canMove[i] = false;
		}
		for (int i = 0; i < 4; i++) {
			sideLights[i].dimGreen();
			sideLights[i].dimYellow();
			sideLights[i].activeRed();
			roadLights[i].dimGreen();
			roadLights[i].dimYellow();
			roadLights[i].activeRed();
		}
		canRoll[3] = true;
			for(int i=1; i<3; i++)
			{
				sideLights[i].dimGreen();
				sideLights[i].activeYellow();
				sideLights[i].dimRed();
			}
	}
}// endl Class
