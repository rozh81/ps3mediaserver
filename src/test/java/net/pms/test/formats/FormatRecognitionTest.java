/*
 * PS3 Media Server, for streaming any medias to your PS3.
 * Copyright (C) 2008  A.Brochard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 2
 * of the License only.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package net.pms.test.formats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import net.pms.configuration.RendererConfiguration;
import net.pms.dlna.DLNAMediaAudio;
import net.pms.dlna.DLNAMediaInfo;
import net.pms.dlna.MediaInfoParser;
import net.pms.formats.DVRMS;
import net.pms.formats.Format;
import net.pms.formats.GIF;
import net.pms.formats.ISO;
import net.pms.formats.JPG;
import net.pms.formats.M4A;
import net.pms.formats.MKV;
import net.pms.formats.MP3;
import net.pms.formats.MPG;
import net.pms.formats.OGG;
import net.pms.formats.PNG;
import net.pms.formats.RAW;
import net.pms.formats.TIF;
import net.pms.formats.WAV;
import net.pms.formats.WEB;
import net.pms.network.HTTPResource;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;


/**
 * Test the recognition of formats.
 */
public class FormatRecognitionTest {
	@Before
	public void setUp() {
		// Silence all log messages from the PMS code that is being tested
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset(); 

		// Initialize the RendererConfiguration
		RendererConfiguration.loadRendererConfigurations();
	}

    /**
     * Test some basic functionality of {@link RendererConfiguration#isCompatible(DLNAMediaInfo, Format)}
     */
    @Test
	public void testRendererConfigurationBasics() {
    	// This test is only useful if the MediaInfo library is available
		if (MediaInfoParser.isValid()) {
			RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
			assertNotNull("No renderer named \"Playstation 3\" found.", conf);
			assertEquals("With nothing provided isCompatible() should return false", false,
					conf.isCompatible(null, null));
		}
	}

	/**
	 * Test the compatibility of the Playstation 3 with the GIF format.
	 */
	@Test
	public void testPlaystationImageGifCompatibility() {
    	// This test is only useful if the MediaInfo library is available
		if (MediaInfoParser.isValid()) {
			RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
			assertNotNull("No renderer named \"Playstation 3\" found.", conf);
	
			// Construct GIF information
			DLNAMediaInfo info = new DLNAMediaInfo();
			info.setContainer("gif");
			Format format = new GIF();
			format.match("test.gif");
			assertEquals("PS3 is reported to be incompatible with GIF", true,
					conf.isCompatible(info, format));
		}
	}

	/**
	 * Test the compatibility of the Playstation 3 with the PNG format.
	 */
	@Test
	public void testPlaystationImagePngCompatibility() {
    	// This test is only useful if the MediaInfo library is available
		if (MediaInfoParser.isValid()) {
			RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
			assertNotNull("No renderer named \"Playstation 3\" found.", conf);
	
			// Construct JPG information
			DLNAMediaInfo info = new DLNAMediaInfo();
			info.setContainer("png");
			Format format = new PNG();
			format.match("test.png");
			assertEquals("PS3 is reported to be incompatible with PNG", true,
					conf.isCompatible(info, format));
		}
	}

	/**
	 * Test the compatibility of the Playstation 3 with the TIFF format.
	 */
	@Test
	public void testPlaystationImageTiffCompatibility() {
    	// This test is only useful if the MediaInfo library is available
		if (MediaInfoParser.isValid()) {
			RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
			assertNotNull("No renderer named \"Playstation 3\" found.", conf);
	
			// Construct JPG information
			DLNAMediaInfo info = new DLNAMediaInfo();
			info.setContainer("tiff");
			Format format = new TIF();
			format.match("test.tiff");
			assertEquals("PS3 is reported to be incompatible with TIFF", true,
					conf.isCompatible(info, format));
		}
	}

	/**
	 * Test the compatibility of the Playstation 3 with the MP3 format.
	 */
	@Test
	public void testPlaystationAudioMp3Compatibility() {
    	// This test is only useful if the MediaInfo library is available
		if (MediaInfoParser.isValid()) {
			RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
			assertNotNull("No renderer named \"Playstation 3\" found.", conf);
	
			// Construct regular two channel MP3 information
			DLNAMediaInfo info = new DLNAMediaInfo();
			info.setContainer("mp3");
			info.setMimeType(HTTPResource.AUDIO_MP3_TYPEMIME);
			DLNAMediaAudio audio = new DLNAMediaAudio();
			audio.setNrAudioChannels(2);
			ArrayList<DLNAMediaAudio> audioCodes = new ArrayList<DLNAMediaAudio>();
			audioCodes.add(audio);
			info.setAudioCodes(audioCodes);
			Format format = new MP3();
			format.match("test.mp3");
			assertEquals("PS3 is reported to be incompatible with MP3", true,
					conf.isCompatible(info, format));
	
			// Construct five channel MP3 that the PS3 does not support natively
			audio.setNrAudioChannels(5);
			assertEquals("PS3 is reported to be incompatible with MP3", false,
					conf.isCompatible(info, format));
		}
	}

	/**
	 * Test the compatibility of the Playstation 3 with the MPG format.
	 */
	@Test
	public void testPlaystationVideoMpgCompatibility() {
    	// This test is only useful if the MediaInfo library is available
		if (MediaInfoParser.isValid()) {
			RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
			assertNotNull("No renderer named \"Playstation 3\" found.", conf);
	
			// Construct regular two channel MPG information
			DLNAMediaInfo info = new DLNAMediaInfo();
			info.setContainer("avi");
			DLNAMediaAudio audio = new DLNAMediaAudio();
			audio.setCodecA("ac3");
			audio.setNrAudioChannels(5);
			ArrayList<DLNAMediaAudio> audioCodes = new ArrayList<DLNAMediaAudio>();
			audioCodes.add(audio);
			info.setAudioCodes(audioCodes);
			info.setCodecV("mp4");
			Format format = new MPG();
			format.match("test.avi");
			assertEquals("PS3 is reported to be incompatible with MPG", true,
					conf.isCompatible(info, format));
	
			// Construct MPG with wmv codec that the PS3 does not support natively
			info.setCodecV("wmv");
			assertEquals("PS3 is reported to be compatible with MPG with wmv codec", false,
					conf.isCompatible(info, format));
		}
	}

	/**
	 * Test the compatibility of the Playstation 3 with the MPG format.
	 */
	@Test
	public void testPlaystationVideoMkvCompatibility() {
    	// This test is only useful if the MediaInfo library is available
		if (MediaInfoParser.isValid()) {
			RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
			assertNotNull("No renderer named \"Playstation 3\" found.", conf);
	
			// Construct MKV information
			DLNAMediaInfo info = new DLNAMediaInfo();
			info.setContainer("mkv");
			DLNAMediaAudio audio = new DLNAMediaAudio();
			audio.setCodecA("ac3");
			audio.setNrAudioChannels(5);
			ArrayList<DLNAMediaAudio> audioCodes = new ArrayList<DLNAMediaAudio>();
			audioCodes.add(audio);
			info.setAudioCodes(audioCodes);
			info.setCodecV("mp4");
			Format format = new MPG();
			format.match("test.mkv");
			assertEquals("PS3 is reported to be incompatible with MKV", false,
					conf.isCompatible(info, format));
		}
	}

	/**
	 * Test the backwards compatibility of
	 * {@link Format#isCompatible(DLNAMediaInfo, RendererConfiguration)} and
	 * {@link Format#ps3compatible()}.
	 * 
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testBackwardsCompatibility() {
    	// This test is only useful if the MediaInfo library is available
		if (MediaInfoParser.isValid()) {
			// Testing ps3compatible(), so use renderer Playstation 3
			RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
			assertNotNull("No renderer named \"Playstation 3\" found.", conf);
	
			// DVRMS: false
			DLNAMediaInfo info = new DLNAMediaInfo();
			info.setContainer("dvr");
			Format format = new DVRMS();
			format.match("test.dvr");
			assertEquals("isCompatible() reporting different outcome than ps3compatible() for DVRMS",
					format.ps3compatible(),	conf.isCompatible(info, format));
	
			// ISO: false
			info = new DLNAMediaInfo();
			info.setContainer("iso");
			format = new ISO();
			format.match("test.iso");
			assertEquals("isCompatible() reporting different outcome than ps3compatible() for ISO",
					format.ps3compatible(),	conf.isCompatible(info, format));
	
			// JPG: true
			info = new DLNAMediaInfo();
			info.setContainer("jpg");
			format = new JPG();
			format.match("test.jpeg");
			assertEquals("isCompatible() reporting different outcome than ps3compatible() for JPG",
					format.ps3compatible(),	conf.isCompatible(info, format));
	
			// M4A: false
			info = new DLNAMediaInfo();
			info.setContainer("m4a");
			format = new M4A();
			format.match("test.m4a");
			assertEquals("isCompatible() reporting different outcome than ps3compatible() for M4A",
					format.ps3compatible(),	conf.isCompatible(info, format));
	
			// MKV: false
			info = new DLNAMediaInfo();
			info.setContainer("mkv");
			format = new MKV();
			format.match("test.mkv");
			assertEquals("isCompatible() reporting different outcome than ps3compatible() for MKV",
					format.ps3compatible(),	conf.isCompatible(info, format));
	
			// MP3: true
			info = new DLNAMediaInfo();
			info.setContainer("mp3");
			format = new MP3();
			format.match("test.mp3");
			assertEquals("isCompatible() reporting different outcome than ps3compatible() for MP3",
					format.ps3compatible(),	conf.isCompatible(info, format));
	
			// MPG: true
			info = new DLNAMediaInfo();
			info.setContainer("avi");
			format = new MPG();
			format.match("test.mpg");
			assertEquals("isCompatible() reporting different outcome than ps3compatible() for MPG",
					format.ps3compatible(),	conf.isCompatible(info, format));
	
			// OGG: false
			info = new DLNAMediaInfo();
			info.setContainer("ogg");
			format = new OGG();
			format.match("test.ogg");
			assertEquals("isCompatible() reporting different outcome than ps3compatible() for OGG",
					format.ps3compatible(),	conf.isCompatible(info, format));
	
			// RAW: false
			info = new DLNAMediaInfo();
			info.setContainer("raw");
			format = new RAW();
			format.match("test.arw");
			assertEquals("isCompatible() reporting different outcome than ps3compatible() for RAW",
					format.ps3compatible(),	conf.isCompatible(info, format));
	
			// WAV: true
			info = new DLNAMediaInfo();
			info.setContainer("wav");
			format = new WAV();
			format.match("test.wav");
			assertEquals("isCompatible() reporting different outcome than ps3compatible() for WAV",
					format.ps3compatible(),	conf.isCompatible(info, format));
	
			// WEB: type=IMAGE
			info = new DLNAMediaInfo();
			info.setContainer("jpg");
			format = new WEB();
			format.match("http://test.org/");
			format.setType(Format.IMAGE);
			assertEquals("isCompatible() reporting different outcome than ps3compatible() for WEB image",
					format.ps3compatible(),	conf.isCompatible(info, format));
	
			// WEB: type=VIDEO
			info = new DLNAMediaInfo();
			info.setContainer("avi");
			format.setType(Format.VIDEO);
			assertEquals("isCompatible() reporting different outcome than ps3compatible() for WEB video",
					format.ps3compatible(),	conf.isCompatible(info, format));
		}
	}
}