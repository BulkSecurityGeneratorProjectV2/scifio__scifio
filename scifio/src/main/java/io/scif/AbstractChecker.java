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

package io.scif;

import io.scif.io.RandomAccessInputStream;
import io.scif.util.FormatTools;

import java.io.IOException;

/**
 * Abstract superclass of all SCIFIO {@link io.scif.Checker} implementations.
 * 
 * @see io.scif.Checker
 * @see io.scif.HasFormat
 * @author Mark Hiner
 */
public abstract class AbstractChecker extends AbstractHasFormat implements
	Checker
{

	// -- Fields --

	/**
	 * Whether the file extension matching one of the format's suffixes is
	 * necessary to identify the file as a source compatible with this format.
	 */
	protected boolean suffixNecessary = true;

	/**
	 * Whether the file extension matching one of the format's suffixes is
	 * sufficient to identify the file as a source compatible with this format.
	 * <p>
	 * If false, the source will have to be read to determine compatibility.
	 * </p>
	 */
	protected boolean suffixSufficient = true;

	// -- Checker API Methods --

	/* @see Checker#isFormat(String name, boolean open) */
	public boolean isFormat(final String name) {
		return isFormat(name, true);
	}

	/* @see Checker#isFormat(String name, boolean open) */
	public boolean isFormat(final String name, final boolean open) {
		// if file extension ID is insufficient and we can't open the file, give up
		if (!suffixSufficient && !open) return false;

		if (suffixNecessary || suffixSufficient) {
			// it's worth checking the file extension
			final boolean suffixMatch =
				FormatTools.checkSuffix(name, getFormat().getSuffixes());

			// if suffix match is required but it doesn't match, failure
			if (suffixNecessary && !suffixMatch) return false;

			// if suffix matches and that's all we need, green light it
			if (suffixMatch && suffixSufficient) return true;
		}

		// suffix matching was inconclusive; we need to analyze the file contents
		if (!open) return false; // not allowed to open any files
		try {
			final RandomAccessInputStream stream =
				new RandomAccessInputStream(getContext(), name);
			final boolean isFormat = isFormat(stream);
			stream.close();
			return isFormat;
		}
		catch (final IOException exc) {
			log().debug("", exc);
			return false;
		}
	}

	/* @see Checker#isFormat(RandomAccessInputStream) */
	public boolean isFormat(final RandomAccessInputStream stream)
		throws IOException
	{
		return false;
	}

	/*
	 * @see io.scif.Checker#checkHeader(byte[])
	 */
	public boolean checkHeader(final byte[] block) {
		try {
			final RandomAccessInputStream stream =
				new RandomAccessInputStream(getContext(), block);
			final boolean isFormat = isFormat(stream);
			stream.close();
			return isFormat;
		}
		catch (final IOException e) {
			log().debug("", e);
		}
		return false;
	}
}