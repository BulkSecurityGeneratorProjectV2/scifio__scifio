/*
 * #%L
 * OME database I/O package for communicating with OME and OMERO servers.
 * %%
 * Copyright (C) 2005 - 2013 Open Microscopy Environment:
 *   - Board of Regents of the University of Wisconsin-Madison
 *   - Glencoe Software, Inc.
 *   - University of Dundee
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package loci.ome.io.services;

import loci.common.services.AbstractService;
import loci.formats.IFormatReader;
import loci.formats.IFormatWriter;
import loci.ome.io.OMEReader;
import loci.ome.io.OMEWriter;
import loci.ome.io.OmeroReader;

import org.openmicroscopy.ds.dto.Image;
import org.scijava.plugin.Plugin;

/**
 * <dl><dt><b>Source code:</b></dt>
 * <dd><a href="http://trac.openmicroscopy.org.uk/ome/browser/bioformats.git/components/ome-io/src/loci/ome/io/services/OMEReaderWriterServiceImpl.java">Trac</a>,
 * <a href="http://git.openmicroscopy.org/?p=bioformats.git;a=blob;f=components/ome-io/src/loci/ome/io/services/OMEReaderWriterServiceImpl.java;hb=HEAD">Gitweb</a></dd></dl>
 *
 * @author callan
 */
@Plugin(type=OMEReaderWriterService.class)
public class OMEReaderWriterServiceImpl extends AbstractService
  implements OMEReaderWriterService
{

  public OMEReaderWriterServiceImpl() {
    // Just being thorough with these dependencies.
    checkClassDependency(OMEReader.class);
    checkClassDependency(OMEWriter.class);
    checkClassDependency(OmeroReader.class);
    checkClassDependency(omero.model.Image.class);
    checkClassDependency(Image.class);
  }

  /* (non-Javadoc)
   * @see loci.formats.OMEReaderWriterService#newOMEROReader()
   */
  public IFormatReader newOMEROReader() {
    return new OmeroReader();
  }

  /* (non-Javadoc)
   * @see loci.formats.OMEReaderWriterService#newOMEROWriter()
   */
  public IFormatWriter newOMEROWriter() {
    throw new IllegalArgumentException("Unavailable OMERO writer.");
  }

  /* (non-Javadoc)
   * @see loci.formats.OMEReaderWriterService#newOMEReader()
   */
  public IFormatReader newOMEReader() {
    return new OMEReader();
  }

  /* (non-Javadoc)
   * @see loci.formats.OMEReaderWriterService#newOMEWriter()
   */
  public IFormatWriter newOMEWriter() {
    return new OMEWriter();
  }

}
