/**
 * Package for groups of classes used together for storing and managing data. Each group is contained in individual
 * package.
 * <hr>
 * Usually each data group consists of:
 *
 * <ol>
 *     <li>
 *         Elements of given group's public API:
 *         <ul>
 *             <li>
 *                 <code>Entity</code>: Pojo class compatible with JPA specification.
 *             </li>
 *             <li>
 *                 <code>Service</code>: Spring's {@link org.springframework.stereotype.Service Service} bean used for processing of data contained in <code>Value</code> objects.
 *             </li>
 *        </ul>
 *     </li>
 *     <li>
 *         Elements of given group's internal API:
 *         <ul>
 *             <li>
 *                 <code>Dao</code>: Spring's {@link org.springframework.stereotype.Repository Repository} bean used for persisting and fetching <code>Entity</code> objects
 *             </li>
 *         </ul>
 *     </li>
 * </ol>
 */
package com.jakubeeee.iotaccess.core.data;