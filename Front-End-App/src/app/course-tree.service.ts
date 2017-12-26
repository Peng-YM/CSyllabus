import { Injectable } from '@angular/core';
import {Tree} from "./Models/tree";

@Injectable()
export class CourseTreeService {

  constructor() { }
  public static findAncestors(tree:Tree, src: number):number[] {
    let ancestors:number[] = [];
    const result:number[] = CourseTreeService.topSort(tree);
    const index:number = result.indexOf(src, 0);
    if(index > -1){
      ancestors = result.slice(0, index);
    }
    return ancestors;
  }

  public static findDecedents(tree: Tree, src: number): number[] {
    let decedents:number[] = [];
    const result:number[] = CourseTreeService.topSort(tree);
    const index:number = result.indexOf(src, 0);
    if(index > -1){
      decedents = result.slice(index, result.length);
    }
    return decedents;
  }

  private static topSort(tree: Tree): number[] {
    let temp = CourseTreeService.buildGraph(tree);
    let graph = temp[0];
    let reverseGraph = temp[1];
    let result: number[] = [];
    let zeroInDegreeSet: number[] = [];
    // initialize zero degree set
    for(let node of tree.nodes){
      if (reverseGraph[node].length === 0){
        zeroInDegreeSet.push(node);
      }
    }

    while(zeroInDegreeSet.length !== 0){
      const n = zeroInDegreeSet.pop();
      result.push(n);
      // for each node m with an edge e from n to m do
      while (graph[n].length !== 0){
        // remove edge e from the graph
        const m = graph[n].pop();
        const index = reverseGraph[m].indexOf(n, 0);
        if (index > -1) {
          reverseGraph[m].splice(index, 1);
        }
        // if m has no other incoming edges, insert it into zero in degree set
        if (reverseGraph[m].length === 0){
          zeroInDegreeSet.push(m);
        }
      }
      // check cycle
      let hasCycle: boolean = false;
      // if graph has edge then raise error
      for( let node of tree.nodes ){
        if (graph[node].length !== 0){
          hasCycle = true;
          break;
        }
      }
      if (hasCycle){
        throw new Error("Graph has at least one cycle!");
      }else{
        return result;
      }
    }
    return result;
  }

  private static buildGraph(tree: Tree): [{[id: number]: number[]}, {[id: number]: number[]}] {
    let reverseGraph: {[id: number]: number[]} = {};
    let graph: {[id: number]: number[]} = {};

    for(let node of tree.nodes){
      let temp: number[] = [];
      let temp1: number[] = [];

      for(let edge of tree.edges){
        if (edge['source'] === node){
          temp.push(edge['target']);
        }
        if (edge['target'] ===  node){
          temp1.push(edge['source']);
        }
      }
      graph[node] = temp;
      reverseGraph[node] = temp1;
    }
    return [graph, reverseGraph];
  }
}
