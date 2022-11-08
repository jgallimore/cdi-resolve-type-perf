/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.superbiz.moviefun;

import org.slf4j.Logger;
import org.superbiz.model.Work;

import javax.ejb.EJB;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version $Revision$ $Date$
 */
@WebServlet("/hello/*")
public class ActionServlet extends HttpServlet {

    private static final long serialVersionUID = -5832176047021911038L;

    @EJB
    private Work workBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            final BeanManager bm = CDI.current().getBeanManager();
            final Bean<?> bean = bm.resolve(bm.getBeans(Logger.class ));
            final CreationalContext<?> context = bm.createCreationalContext(bean);
            final Logger logger = (Logger) bm.getReference(bean, Logger.class, context);
        } catch (Exception e) {
            throw new ServletException("Unable to obtain logger", e);
        }

        workBean.doWork();
        request.getRequestDispatcher("WEB-INF/hello.jsp").forward(request, response);
    }
}
