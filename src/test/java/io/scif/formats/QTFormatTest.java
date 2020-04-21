/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2019 SCIFIO developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package io.scif.formats;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import net.imagej.axis.Axes;

import org.junit.Test;
import org.scijava.io.http.HTTPLocation;

public class QTFormatTest extends AbstractFormatTest {

	public QTFormatTest() throws URISyntaxException, MalformedURLException {
		super(new HTTPLocation("https://samples.scif.io/wtembryo.zip"));
	}

	private static final String hash_embryo =
		"491fc510995691caa21b383a13618876a97b2013";

	@Test
	public void testWTEmbryo() {
		final String meta =
			"{\"pixelOffset\":16,\"pixelBytes\":14042503,\"bitsPerPixel\":32,\"rawSize\":0,\"offsets\":[16,139963,274731,409069,542277,669405,797995,925863,1059889,1196103,1329700,1461521,1591359,1722190,1851627,1982388,2110768,2238684,2367473,2497503,2626804,2754454,2881939,3009233,3134020,3257286,3382146,3512539,3642020,3773360,3902703,4033752,4163282,4294128,4423116,4551375,4678572,4807989,4941681,5072643,5201280,5331089,5462682,5592162,5724267,5860838,5996122,6128993,6260181,6394815,6530939,6663652,6795537,6924497,7053143,7181907,7317204,7452951,7588563,7720739,7851817,7983845,8109558,8238666,8365686,8489586,8613072,8743223,8871795,9001040,9127961,9255817,9381460,9504357,9633140,9760355,9886522,10016342,10144806,10278394,10408663,10538374,10665950,10791902,10919627,11050286,11180803,11311952,11443711,11575227,11702426,11830792,11962840,12094776,12227376,12359739,12491829,12623148,12752792,12882986,13011937,13142072,13268527,13394166,13521642,13651562,13781299,13913368],\"prevPlane\":0,\"canUsePrevious\":false,\"codec\":\"rle \",\"altPlanes\":0,\"scale\":0,\"chunkSizes\":[108,139947,134768,134338,133208,127128,128590,127868,134026,136214,133597,131821,129838,130831,129437,130761,128380,127916,128789,130030,129301,127650,127485,127294,124787,123266,124860,130393,129481,131340,129343,131049,129530,130846,128988,128259,127197,129417,133692,130962,128637,129809,131593,129480,132105,136571,135284,132871,131188,134634,136124,132713,131885,128960,128646,128764,135297,135747,135612,132176,131078,132028,125713,129108,127020,123900,123486,130151,128572,129245,126921,127856,125643,122897,128783,127215,126167,129820,128464,133588,130269,129711,127576,125952,127725,130659,130517,131149,131759,131516,127199,128366,132048,131936,132600,132363,132090,131319,129644,130194,128951,130135,126455,125639,127476,129920,129737,132069],\"interlaced\":false,\"spork\":false,\"flip\":false,\"filtered\":false,\"datasetName\":\"wtembryo.mov\",\"table\":{\"Bits per pixel\":32,\"Codec\":\"rle \",\"Frames per second\":60},\"priority\":0.0}";
		testImg(baseFolder().child("wtembryo.mov"), hash_embryo, meta, new int[] {
			320, 240, 3, 108 }, Axes.X, Axes.Y, Axes.CHANNEL, Axes.TIME);
	}

}
