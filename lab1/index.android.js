/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  Alert,
  StyleSheet,
  Text,
  View,
  Navigator,
  ListView,
  TextInput,
  StatusBar,
  TouchableHighLight,
  TouchableNativeFeedback,
  TouchableOpacity,
  AsyncStorage
} from 'react-native';

import Communications from 'react-native-communications';
import Button from 'react-native-button';
import {BarChart} from 'react-native-charts';

var clients = []

var SCREEN_WIDTH = require('Dimensions').get('window').width;

var BaseConfig = Navigator.SceneConfigs.FloatFromRight;

var CustomLeftToRightGesture = Object.assign({}, BaseConfig.gestures.pop, {
  // Make it snap back really quickly after canceling pop
  snapVelocity: 8,
  // Make it so we can drag anywhere on the screen
  edgeHitWidth: SCREEN_WIDTH,
});

var CustomSceneConfig = Object.assign({}, BaseConfig, {
  // A very tighly wound spring will make this transition fast
  springTension: 100,
  springFriction: 1,
  // Use our custom gesture defined above
  gestures: {
    pop: CustomLeftToRightGesture,
  }
});

class ReactMain extends React.Component{

  constructor(props){
    super(props);

    if(clients.length == 0){
      this._getPersistedData();
    }

    this.state = {
      username : '',
      password : '',
      email : '',
      dataSource : new ListView.DataSource({
        rowHasChanged: (row1, row2) => row1 != row2,
      }),
      loaded: false,
    };
  }

  componentDidMount(){
        this.setState({
            dataSource: this.state.dataSource.cloneWithRows(clients),
            loaded: true,
        });
	}

  _persistData(){
      return AsyncStorage.setItem('key1', JSON.stringify(clients))
                          .then(json => console.log('success! at persist save'))
                          .catch(error => console.log('error! at persist save'));
    }

  _getPersistedData(){
    return AsyncStorage.getItem('key1')
        .then(req => JSON.parse(req))
        .then(json => {

                  console.log(json)

                  for (var i = 0; i < json.length; i++) {
                    clients.push({ "username": json[i].username, "password": json[i].password, "email" : json[i].email});

                    this.setState({
                         dataSource: this.state.dataSource.cloneWithRows(clients),
                         loaded: true,
                     });
                }
        })
        .catch(error => console.log('Error at reading!'));

    }

    _addBtn(){
       if( this.state.username !== '' && this.state.password != '' && this.state.email != '')
         {
         clients.push({ "username": this.state.username, "password": this.state.password, "email" : this.state.email});
         Alert.alert("Done","Client added");
         this.setState({
           dataSource: this.state.dataSource.cloneWithRows(clients),
           loaded: true,
         });

         this._persistData();

         }
       else{
         Alert.alert("Warning","Some input is empty");
       }

     }


   _emailBtn() {
        var clientsString = clients.map(function(item) {
              return "\nUsername: " + item['username'] + "\nPassword: " + item['password'] + "\nEmail: " + item['email'] +  "\n";
         });

        Communications.email(["ruspauladrian.1994@gmail.com"],"","","Sent from react",clientsString.toString());
    }

    renderClient(client){
      return(
        <TouchableOpacity onPress={ () => this._navigate(client)}>
          <View style = {styles.ViewDetails}>
            <Text>{client.username}</Text>
            <Text>{client.password}</Text>
            <Text>{client.email}</Text>
          </View>
        </TouchableOpacity>
      );
    }

    render() {
     return (
       <View style={{backgroundColor: 'white'}}>
           <Text style={styles.header}>Welcome</Text>

           <TextInput
             style= {styles.input}
             onChangeText={(text) => this.setState({username : text})}
             placeholder="Username..."
             value = {this.state.username}
           />
           <TextInput
             style={styles.input}
             onChangeText={(text) => this.setState({password : text})}
             placeholder="Password..."
             value = {this.state.password}
           />
           <TextInput
             style={styles.input}
             onChangeText={(text) => this.setState({email : text})}
             placeholder="Email..."
             value = {this.state.email}
           />

            <Button
             containerStyle={{padding:10, height:45, overflow:'hidden', borderRadius:4, backgroundColor: 'green', marginBottom: 4}}
               style={{fontSize: 20, color: 'white'}}
               styleDisabled={{color: 'red'}}
               onPress={() => this._addBtn()}>
               Add client
           </Button>

           <Button
             containerStyle={{padding:10, height:45, overflow:'hidden', borderRadius:4, backgroundColor: 'green'}}
               style={{fontSize: 20, color: 'white'}}
               styleDisabled={{color: 'red'}}
               onPress={() => this._emailBtn()}>
               Send email
           </Button>



           <ListView
             dataSource={this.state.dataSource}
             renderRow={this.renderClient.bind(this)}
             style={styles.listView}
           />

     </View>

     );
   }
}
class EditDetails extends React.Component{

  constructor(props){
    super(props);
    this.state = {
        username : this.props.client.username ,
        password: this.props.client.password,
        email: this.props.client.email}
  }


  _persistData(){
        return AsyncStorage.setItem('key1', JSON.stringify(clients))
                            .then(json => console.log('success! at persist save'))
                            .catch(error => console.log('error! at persist save'));
      }

  _handlePressSave() {
      if( this.state.username !== '' && this.state.password != '' && this.state.email != '' ){
          this.props.client.username = this.state.username;
          this.props.client.password = this.state.password;
          this.props.client.email = this.state.email;
          Alert.alert("Done","Saved");

          this._persistData();
          this.props.navigator.pop();
      }else{
        Alert.alert("Warning","Some input is empty");
      }
  }

  _handlePressDelete(){
      console.log("------------------------ DELETE start  -------------");


      var index = clients.indexOf(this.props.client);
      console.log("index = " + index);

      console.log("current length of array: " + clients.length)
      if (index > -1) {
          clients.splice(index, 1);
          console.log("new length of array: " + clients.length)
          Alert.alert("Done","Deleted");

          this._persistData();

          this.props.navigator.push({
    	        name: 'ReactImpl',
          })
        }
        else{
          Alert.alert("Warning","Client not found. Can't delete");
        }
        console.log("------------------------ DELETE gata  -------------");
  }

  _handlePressChart(){
       this.props.navigator.push({
             name: 'ChartPage',
         })
   }

   render(){
      return(
        <View style={{backgroundColor: 'white'}}>
          <Text style={styles.header}>Edit details of the client</Text>

          <TextInput
            style= {styles.input}
            onChangeText={(text) => this.setState({username : text})}
            placeholder="Username..."
            value = {this.state.username}
          />
          <TextInput
            style={styles.input}
            onChangeText={(text) => this.setState({password : text})}
            placeholder="Password..."
            value = {this.state.password}
          />
          <TextInput
            style={styles.input}
            onChangeText={(text) => this.setState({email : text})}
            placeholder="Email..."
            value = {this.state.email}
          />


          <Button
            containerStyle={{padding:10, height:45, overflow:'hidden', borderRadius:4, backgroundColor: 'green', marginBottom: 4}}
              style={{fontSize: 20, color: 'white'}}
              styleDisabled={{color: 'red'}}
              onPress={ () => this._handlePressSave() }>
              Save and return
		      </Button>


          <Button
            containerStyle={{padding:10, height:45, overflow:'hidden', borderRadius:4, backgroundColor: 'red', marginBottom: 4}}
              style={{fontSize: 20, color: 'white'}}
              styleDisabled={{color: 'red'}}
              onPress={ () => this._handlePressDelete() }>
              Delete
		      </Button>

          <Button
            containerStyle={{padding:10, height:45, overflow:'hidden', borderRadius:4, backgroundColor: 'blue', marginBottom: 4}}
              style={{fontSize: 20, color: 'white'}}
              styleDisabled={{color: 'red'}}
              onPress={ () => this._handlePressChart() }>
              Show chart
		      </Button>


        </View>
      )
    }

}

chartDataSource = []

class ChartPage extends React.Component{
  constructor(props){
    super(props);
    this.state = {
      top1 : '',
      top2 : '',
      top3 : ''
    }
    this._calculateChartValues();
  }

  _handlePressBack(){
      this.props.navigator.pop();
  }

  _handlePressHome(){
    this.props.navigator.push({
	        name: 'ReactImpl',
      })
  }

  _calculateChartValues(){
          console.log("------ Entered in calculateChartValues()------");

          var dict = [];
          for(var i = 0 ; i < requests.length ; i++){

                var found = false;

                for(var j = 0; j < dict.length; j++) {
                    if (dict[j].key == requests[i].email) {
                        found = true;
                        break;
                    }
                }

                if(found == false){
                  dict.push({
                    key:   requests[i].email,
                    value: 1
                  });
                }else if(found == true){ // daca era deja in dictionar ii incrementez valoarea
                  for(var k = 0; k < dict.length; k++) {
                    if (dict[k].key == requests[i].email) {
                        dict[k].value = dict[k].value + 1
                        break;
                    }
                  }
                }
          }

          for(var l = 0; l < dict.length; l++) {

                console.log("key: " + dict[l].key + " value: " + dict[l].value)
           }

           var max = {
                  name: '',
                  value: 0
                }


           for(var i = 0 ; i< dict.length ; i++){
             if(dict[i].value > max.value){
               max.name = dict[i].key;
               max.value = dict[i].value;
             }
           }

           chartDataSource.push({
                  fillColor: 'red',
                  data: [{ value: max.value }]
                });

          this.state.top1 = max.name;


          for( var i = 0; i< dict.length ; i++){
            if(dict[i].key == max.name){
              dict.splice(i, 1);
            }
          }

          if(dict.length > 0){
            var max2 = {
                  name: '',
                  value: 0
                }

            for(var i = 0 ; i< dict.length ; i++){
             if(dict[i].value > max2.value){
               max2.name = dict[i].key;
               max2.value = dict[i].value;
             }
           }

           chartDataSource.push({
                  fillColor: 'orange',
                  data: [{ value: max2.value }]
                });
          }

          this.state.top2 = max2.name;

          for( var i = 0; i< dict.length ; i++){
            if(dict[i].key == max2.name){
              dict.splice(i, 1);
            }
          }


          if(dict.length > 0){
            var max3 = {
                  name: '',
                  value: 0
                }

            for(var i = 0 ; i< dict.length ; i++){
             if(dict[i].value > max3.value){
               max3.name = dict[i].key;
               max3.value = dict[i].value;
             }
           }

           chartDataSource.push({
                  fillColor: 'yellow',
                  data: [{ value: max3.value }]
                });
          }
          this.state.top3 = max3.name;

      }

      render(){
        return(
          <View style={{backgroundColor: 'cyan'}}>
            <Text style={styles.header}>Chart page</Text>

             <BarChart
                dataSets= {chartDataSource}
                graduation={1}
                horizontal={false}
                showGrid={true}
                barSpacing={5}
                style={{
                  height: 300,
                  margin: 15,
                }}/>


                <Text ><Text style={{color: 'red'}}>Red</Text>: {this.state.top1} | <Text style={{color: 'orange'}}>Orange</Text>: {this.state.top2} | <Text style={{color: 'yellow'}}>Yellow</Text>: {this.state.top3}</Text>
                <Button
                  containerStyle={{padding:10, height:45, overflow:'hidden', borderRadius:4, backgroundColor: 'green', marginBottom: 4}}
                    style={{fontSize: 20, color: 'white'}}
                    styleDisabled={{color: 'red'}}
                    onPress={ () => this._handlePressBack() }>
                    Back
  		         </Button>

              <Button
                containerStyle={{padding:10, height:45, overflow:'hidden', borderRadius:4, backgroundColor: 'red', marginBottom: 4}}
                  style={{fontSize: 20, color: 'white'}}
                  styleDisabled={{color: 'red'}}
                  onPress={ () => this._handlePressHome() }>
                  Home
              </Button>


          </View>
        )
    }
}

var App = React.createClass({
  renderScene(route, navigator) {
  	if(route.name == 'ReactImpl') {
    	return <ReactImpl navigator={navigator} {...route.passProps}  />
    }
    if(route.name == 'EditDetails') {
    	return <EditDetails navigator={navigator} {...route.passProps}  />
    }
    if(route.name == 'ChartPage') {
    	return <ChartPage navigator={navigator} {...route.passProps}  />
    }
  },

  render() {
    return (
      <Navigator
      	style={{ flex:1 }}
        initialRoute={{ name: 'ReactImpl' }}
        renderScene={ this.renderScene } />
    )
  }
});


const styles = StyleSheet.create({

  input: {
    backgroundColor: 'white',
    height: 40,
    borderColor: 'white',
    borderWidth: 1,
    margin: 3,
  },
  listView: {
      width: 320,
      paddingTop: 1,
      backgroundColor: '#F5FCFF',
    },
  header: {
    fontWeight: 'bold',
    fontSize: 30,
    textAlign : 'center',
    color: 'black'

  },
  holder: {
    flex: 0.25,
    justifyContent: 'center',
  },
  text: {
    fontSize: 50,
    backgroundColor: 'red'
  },
  viewDetails: {
	  margin: 9
  }

});
AppRegistry.registerComponent('ReactImpl', () => App);
