/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.adobe.api.platform.runtime.metrics
import java.net.ServerSocket

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.Config

trait EventsTestHelper {

  protected def createConsumer(kport: Int, globalConfig: Config)(implicit system: ActorSystem,
                                                                 materializer: ActorMaterializer) = {
    val settings = OpenWhiskEvents
      .eventConsumerSettings(OpenWhiskEvents.defaultConsumerConfig(globalConfig))
      .withBootstrapServers(s"localhost:$kport")
    KamonConsumer(settings)
  }

  protected def freePort(): Int = {
    val socket = new ServerSocket(0)
    try socket.getLocalPort
    finally if (socket != null) socket.close()
  }
}
