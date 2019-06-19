# ChatBash Project
### 2nd project for SAD Assignemnt of UPC Engineering of Telecomunicacions ETSETB.

#### ChatBash is a chat between multiple clients based on JAVA and one multi-threading Server.
  * Server is based on Selector NIO and Reactor pattern.
  * Client is multithreading: main thread works as the collector information from the user, whereas the ClientThread communicates with the server and also process the information.

As we can see, ChatClient uses Bash to ease the interaction of user.
Main aspects in Bash style is the difference of users by colouring and also the fact that the written line is erased and printed as a message, also coloured, in order to make the reading comprehension easier.

The importance of the project was based on the Pattern reactor.
Furthermore, there was a compulsory rule of creating two classes based on Socket and ServerSocket which only make try-catch statements in order not to throw exceptions.

