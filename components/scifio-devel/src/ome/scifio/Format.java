/*
 * #%L
 * OME SCIFIO package for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2005 - 2013 Open Microscopy Environment:
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
package ome.scifio;

/**
 * Interface for all SCIFIO formats.
 * <p>
 * The {@code Format} is a bag for all other SCIFIO components associated
 * with image IO in a given image format. It acts as a bridge and a black box
 * for creating components, and facilitates component intercommunication.
 * </p>
 * <p>
 * {@code Formats} are typically accessed through the 
 * {@link ome.scifio.services.FormatService}, which maintains a list of
 * singleton {@code Formats} and has many convneience methods relating
 * to their use.
 * </p>
 *
 * <dl><dt><b>Source code:</b></dt>
 * <dd><a href="">Trac</a>,
 * <a href="">Gitweb</a></dd></dl>
 * 
 * @author Mark Hiner
 * 
 * @see ome.scifio.SCIFIO
 * @see ome.scifio.services.FormatService
 */
public interface Format extends ScifioPlugin, HasSCIFIO {
  
  // -- Format API methods --

  /** 
   * Gets the name of this file format.
   * 
   * @return a String representation of this Format's name
   */
  String getFormatName();

  /**
   * Gets the default file suffixes for this file format.
   * 
   * @return an array of extensions associated with this Format
   */
  String[] getSuffixes();

  /**
   * Create an instance of the Metadata associated with this format.
   * 
   * @return A new {@link ome.scifio.Metadata} instance compatible
   *         with this Format's components
   * @throws FormatException
   */
  Metadata createMetadata() throws FormatException;

  /**
   * Create an instance of the Checker associated with this format.
   * 
   * @return A new {@link ome.scifio.Checker} instance which can
   *         determine if sources are compatible with this Format.
   * @throws FormatException
   */
  Checker createChecker() throws FormatException;

  /**
   * Create an instance of the Parser associated with this format.
   * 
   * @return A new {@link ome.scifio.Parser} instance capable of
   *         populating Metadata compatible with this Format.
   * @throws FormatException
   */
  Parser createParser() throws FormatException;

  /**
   * Creates an instance of the Reader associated with this format.
   * 
   * @return A new {@link ome.scifio.Reader} instance capable of
   *         reading datasets of this Format.
   * @throws FormatException
   */
  Reader createReader() throws FormatException;

  /**
   * Creates an instance of the Writer associated with this format.
   * 
   * @return A new {@link ome.scifio.Writer} instance capable of
   *         writing datasets of this Format.
   * @throws FormatException
   */
  Writer createWriter() throws FormatException;
   
  /**
   * @return The class of the Metadata associated with this format
   */
  Class<? extends Metadata> getMetadataClass();

  /**
   * @return The class of the Checker associated with this format
   */
  Class<? extends Checker> getCheckerClass();

  /**
   * @return The class of the Parser associated with this format
   */
  Class<? extends Parser> getParserClass();

  /**
   * @return The class of the Reader associated with this format
   */
  Class<? extends Reader> getReaderClass();

  /**
   * @return The class of the Writer associated with this format
   */
  Class<? extends Writer> getWriterClass();
}
