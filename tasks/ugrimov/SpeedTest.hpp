//
//  SpeedTest.hpp
//  Prjctr
//
//  Created by Artem on 24.09.16.
//  Copyright © 2016 Artem. All rights reserved.
//

#ifndef SpeedTest_hpp
#define SpeedTest_hpp

#include <time.h>


#ifndef speedtest__
#define speedtest__(data)   for (long blockTime = 0; (blockTime == 0 ? (blockTime = clock()) != 0 : false); printf(data "%.9fs", (double) (clock() - blockTime) / CLOCKS_PER_SEC))
#endif


#endif /* SpeedTest_hpp */
