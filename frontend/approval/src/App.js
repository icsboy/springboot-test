import React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";
import './App.css';
import MainComponent from './components/MainComponent';
import WriterComponent from './components/writer/WriterComponent';
import ApproverComponent from './components/approver/ApproverComponent';

function App() {
  return (
    <Router>
        <Switch>
            <Route exact path='/' component={MainComponent} />
            <Route path='/writer' component={WriterComponent} />
            <Route path='/approver' component={ApproverComponent} />
        </Switch>
    </Router>
  );
}

export default App;
