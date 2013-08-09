/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2013 Open Microscopy Environment:
 *   - Board of Regents of the University of Wisconsin-Madison
 *   - Glencoe Software, Inc.
 *   - University of Dundee
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
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

package io.scif.img.utests;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;
import io.scif.img.ImgIOException;
import io.scif.img.ImgOpener;
import io.scif.img.ImgOptions;
import io.scif.img.SubRegion;
import io.scif.img.cell.SCIFIOCellImgFactory;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.ImgPlus;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.img.planar.PlanarImgFactory;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

import org.testng.annotations.Test;

/**
 * Tests for the {@link ImgOpener} class.
 * 
 * @author Mark Hiner
 */
@Test
public class ImgOpenerTest {

	// Use the default constructor, which constructs a minimal context,
	// to ensure all necessary services are present
	private final ImgOpener imgOpener = new ImgOpener();
	private final String id =
		"testImg&sizeX=512&sizeY=512&sizeT=5&dimOrder=XYTZC.fake";

	/**
	 * Check that having raw typed ImgOpener methods doesn't cause problems. This
	 * test should just fail to compile if there's an issue.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testGenerics() throws IncompatibleTypeException, ImgIOException {
		final NativeType[] nativeTypes =
			new NativeType[] { new UnsignedByteType() };

		for (final NativeType t : nativeTypes)
			doTestGenerics(t);

	}

	/**
	 * Test opening images with various ImgOptions set.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testImgOptions() throws IncompatibleTypeException, ImgIOException
	{
		NativeType t = new UnsignedByteType();

		ImgFactory aif = new ArrayImgFactory().imgFactory(t);
		ImgFactory pif = new PlanarImgFactory().imgFactory(t);
		ImgFactory sif = new SCIFIOCellImgFactory().imgFactory(t);

		for (ImgFactory f : new ImgFactory[] { aif, pif, sif }) {
			testSubRegion(f);
		}
	}

	// Tests the opening various sub-regions of an image
	@SuppressWarnings({ "rawtypes" })
	private void testSubRegion(ImgFactory factory) throws ImgIOException {
		ImgOptions options = new ImgOptions();
		// should get an inner left left 128x128 square
		options.setRegion(new SubRegion("128-255", "128-255"));
		doTestSubRegion(factory, options, 128 * 128 * 5);

		// should get the first, 3rd and 5th T slices
		options.setRegion(new SubRegion("0-511", "0-511", "0,2-4:2"));
		doTestSubRegion(factory, options, 512 * 512 * 3);

		// should get the whole image
		options.setRegion(null);
		doTestSubRegion(factory, options, 512 * 512 * 5);
	}

	@SuppressWarnings("rawtypes")
	private void
		doTestSubRegion(ImgFactory factory, ImgOptions options, long size)
			throws ImgIOException
	{
		ImgPlus imgPlus = null;
		imgPlus = imgOpener.openImg(id, factory, options);
		assertNotNull(imgPlus);
		assertEquals(size, imgPlus.size());
	}

	@SuppressWarnings("unchecked")
	private <T> void doTestGenerics(final T type)
		throws IncompatibleTypeException, ImgIOException
	{
		ImgPlus<T> imgPlus = null;

		@SuppressWarnings("rawtypes")
		final ImgFactory factory = new ArrayImgFactory().imgFactory(type);

		// Try each rawtype openImg method
		imgPlus = imgOpener.openImg(id);
		assertNotNull(imgPlus);
		imgPlus = null;
		imgPlus = imgOpener.openImg(id, new ImgOptions());
		assertNotNull(imgPlus);
		imgPlus = null;
		imgPlus = imgOpener.openImg(id, factory);
		assertNotNull(imgPlus);
		imgPlus = null;
	}
}